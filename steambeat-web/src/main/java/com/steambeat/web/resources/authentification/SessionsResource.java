package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.*;
import com.steambeat.web.ReferenceBuilder;
import org.joda.time.*;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class SessionsResource extends ServerResource {

    @Inject
    public SessionsResource(final UserService userService, final SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Post
    public void post(final Form form) {
        if (checkForm(form)) {
            final String email = form.getFirstValue("email");
            final String password = form.getFirstValue("password");
            try {
                user = userService.authentificate(email, password);
                session = sessionService.getSessionFor(user);
                setCookiesInResponse();
                setStatus(Status.SUCCESS_CREATED);
                setLocationRef(new ReferenceBuilder(getContext()).buildUri("/"));
            } catch (BadPasswordException badPasswordException) {
                setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            }
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private boolean checkForm(final Form form) {
        return form.getQueryString().contains("email") && form.getQueryString().contains("password");
    }

    private void setCookiesInResponse() {
        setIdCookie();
        setTokenCookie();
    }

    private void setIdCookie() {
        final CookieSetting id = new CookieSetting();
        id.setName("id");
        id.setComment("id cookie");
        id.setAccessRestricted(true);
        id.setValue(user.getEmail());
        id.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        id.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        id.setMaxAge(FIVE_YEARS);
        id.setPath("/");
        this.getResponse().getCookieSettings().add(id);
    }

    private void setTokenCookie() {
        final CookieSetting session = new CookieSetting();
        session.setName("session");
        session.setComment("session cookie");
        session.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        session.setAccessRestricted(true);
        session.setValue(this.session.getToken().toString());
        session.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        session.setMaxAge(oneHour());
        session.setPath("/");
        this.getResponse().getCookieSettings().add(session);
    }

    private int oneHour() {
        return ((int) new Interval(new DateTime(), session.getExpirationDate()).toDurationMillis()) / 1000;
    }

    @Delete
    public Representation delete() {
        final Cookie sessionCookie = getRequest().getCookies().getFirst("session");
        final Cookie id = getRequest().getCookies().getFirst("id");
        if (sessionCookie != null && id != null) {
            final User user = userService.getUser(id.getValue());
            sessionService.deleteSessionFor(user);
            setEraseIdCookie(id.getValue());
            setEraseSessionCookie(sessionCookie.getValue());
            setStatus(Status.SUCCESS_ACCEPTED);
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        return new EmptyRepresentation();
    }

    private void setEraseIdCookie(final String value) {
        final CookieSetting id = new CookieSetting();
        id.setName("id");
        id.setComment("id cookie");
        id.setAccessRestricted(true);
        id.setValue(value);
        id.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        id.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        id.setMaxAge(0);
        id.setPath("/");
        this.getResponse().getCookieSettings().add(id);
    }

    private void setEraseSessionCookie(final String value) {
        final CookieSetting session = new CookieSetting();
        session.setName("session");
        session.setComment("session cookie");
        session.setSecure(true);
        session.setAccessRestricted(true);
        session.setValue(value);
        session.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        session.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        session.setMaxAge(0);
        session.setPath("/");
        this.getResponse().getCookieSettings().add(session);
    }

    private UserService userService;
    private SessionService sessionService;
    private User user;
    private Session session;
    private static int FIVE_YEARS = 157680000;
}
