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
            } catch (BadUserException badUserException) {
                setStatus(Status.CLIENT_ERROR_FORBIDDEN);
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
        final CookieSetting token = new CookieSetting();
        token.setName("token");
        token.setComment("token cookie");
        token.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        token.setAccessRestricted(true);
        token.setValue(session.getToken().toString());
        token.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        token.setMaxAge(oneHour());
        token.setPath("/");
        this.getResponse().getCookieSettings().add(token);
    }

    private int oneHour() {
        return ((int) new Interval(new DateTime(), session.getExpirationDate()).toDurationMillis()) / 1000;
    }

    @Delete
    public Representation delete() {
        final Cookie token = getRequest().getCookies().getFirst("token");
        final Cookie id = getRequest().getCookies().getFirst("id");
        if (token != null && id != null) {
            final User user = userService.getUser(id.getValue());
            sessionService.deleteSessionFor(user);
            setEraseIdCookie(id.getValue());
            setEraseTokenCookie(token.getValue());
            setStatus(Status.SUCCESS_ACCEPTED);
            setLocationRef(new ReferenceBuilder(getContext()).buildUri("/"));
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

    private void setEraseTokenCookie(final String value) {
        final CookieSetting token = new CookieSetting();
        token.setName("token");
        token.setComment("token cookie");
        token.setSecure(true);
        token.setAccessRestricted(true);
        token.setValue(value);
        token.setSecure(Boolean.valueOf(getContext().getAttributes().get("com.steambeat.cookie.secure").toString()));
        token.setDomain(getContext().getAttributes().get("com.steambeat.cookie.domain").toString());
        token.setMaxAge(0);
        token.setPath("/");
        this.getResponse().getCookieSettings().add(token);
    }

    private UserService userService;
    private SessionService sessionService;
    private User user;
    private Session session;
    private static int FIVE_YEARS = 157680000;
}
