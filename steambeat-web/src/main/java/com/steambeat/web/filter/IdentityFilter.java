package com.steambeat.web.filter;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import org.restlet.*;
import org.restlet.data.Cookie;
import org.restlet.routing.Filter;

public class IdentityFilter extends Filter {

    @Inject
    public IdentityFilter(final UserService userService) {
        this.userService = userService;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        Cookie identity = getIdentityCookieFrom(request);
        if (identity != null) {
            getContext().getAttributes().put("com.steambeat.identity", userService.getName(identity.getValue()));
        } else {
            getContext().getAttributes().put("com.steambeat.identity", "");
        }
        return CONTINUE;
    }

    private Cookie getIdentityCookieFrom(final Request request) {
        return request.getCookies().getFirst("id");
    }

    private UserService userService;
}
