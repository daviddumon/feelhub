package com.steambeat.web.filter;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.user.User;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.routing.Filter;

public class IdentityFilter extends Filter {

    @Inject
    public IdentityFilter(final UserService userService, final SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Cookie identityCookie = getIdentityCookieFrom(request);
        if (identityCookie != null) {
            try {
                user = userService.getUser(identityCookie.getValue());
                request.getAttributes().put("com.steambeat.user", user);
                request.getAttributes().put("com.steambeat.authentificated", sessionService.validateCookieForUser(getSessionCookie(request), user));
            } catch (Exception e) {

            }
        }
        return CONTINUE;
    }

    private Cookie getIdentityCookieFrom(final Request request) {
        return request.getCookies().getFirst("id");
    }

    private Cookie getSessionCookie(final Request request) {
        return request.getCookies().getFirst("session");
    }

    private UserService userService;
    private SessionService sessionService;
    private User user;
}
