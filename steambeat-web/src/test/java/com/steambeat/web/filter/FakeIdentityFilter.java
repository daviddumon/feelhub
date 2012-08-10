package com.steambeat.web.filter;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import org.restlet.*;

public class FakeIdentityFilter extends IdentityFilter {

    @Inject
    public FakeIdentityFilter(final UserService userService) {
        super(userService);
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }
}
