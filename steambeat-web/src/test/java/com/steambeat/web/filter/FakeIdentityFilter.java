package com.steambeat.web.filter;

import com.google.inject.Inject;
import com.steambeat.application.*;
import org.restlet.*;

public class FakeIdentityFilter extends IdentityFilter {

    @Inject
    public FakeIdentityFilter(final UserService userService, final SessionService sessionService) {
        super(userService, sessionService);
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }
}
