package com.feelhub.web.filter;

import com.feelhub.application.*;
import com.google.inject.Inject;
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
