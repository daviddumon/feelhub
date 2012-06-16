package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.web.ReferenceBuilder;
import org.joda.time.*;
import org.restlet.data.*;
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
            } catch (UserException userException) {
                setStatus(Status.CLIENT_ERROR_FORBIDDEN);
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
        id.setSecure(true);
        id.setAccessRestricted(true);
        id.setValue(user.getEmail());
        id.setDomain(getContext().getAttributes().get("com.steambeat.cookie").toString());
        id.setMaxAge(COOKIE_ID_MAX_AGE);
        id.setPath("/");
        this.getResponse().getCookieSettings().add(id);
    }

    private void setTokenCookie() {
        final CookieSetting id = new CookieSetting();
        id.setName("token");
        id.setComment("token cookie");
        id.setSecure(true);
        id.setAccessRestricted(true);
        id.setValue(session.getToken().toString());
        id.setDomain(getContext().getAttributes().get("com.steambeat.cookie").toString());
        final int maxage = ((int) new Interval(new DateTime(), session.getExpirationDate()).toDurationMillis()) / 1000;
        id.setMaxAge(maxage);
        id.setPath("/");
        this.getResponse().getCookieSettings().add(id);
    }

    private UserService userService;
    private SessionService sessionService;
    private User user;
    private Session session;
    private static int COOKIE_ID_MAX_AGE = 157680000;
}
