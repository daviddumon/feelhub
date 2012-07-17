package com.steambeat.web.test.guice;

import com.google.inject.Inject;
import com.steambeat.application.UserService;
import com.steambeat.web.filter.IdentityFilter;
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
