package com.feelhub.web.filter;

import com.feelhub.application.*;
import com.feelhub.domain.user.User;
import com.google.inject.Inject;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.routing.Filter;

import java.util.UUID;

public class IdentityFilter extends Filter {

    @Inject
    public IdentityFilter(final UserService userService, final SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Cookie identityCookie = getIdentityCookieFrom(request);
        try {
            if (identityCookie != null) {
                User user = userService.getUser(identityCookie.getValue());
                request.getAttributes().put("com.feelhub.user", user);
                request.getAttributes().put("com.feelhub.authentificated", sessionService.authentificate(user, getToken(request)));
            }
        } catch (Exception e) {
        }
        return CONTINUE;
    }

    private Cookie getIdentityCookieFrom(final Request request) {
        return request.getCookies().getFirst("id");
    }

    private UUID getToken(final Request request) {
        final Cookie session = request.getCookies().getFirst("session");
        if (session != null) {
            return UUID.fromString(session.getValue());
        } else {
            return null;
        }
    }

    private final UserService userService;
    private final SessionService sessionService;

}
