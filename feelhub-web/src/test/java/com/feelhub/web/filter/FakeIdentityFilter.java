package com.feelhub.web.filter;

import com.feelhub.web.authentification.AuthenticationManager;
import com.google.inject.Inject;
import org.restlet.Request;
import org.restlet.Response;

public class FakeIdentityFilter extends IdentityFilter {

    @Inject
    public FakeIdentityFilter(AuthenticationManager manager) {
        super(manager);
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }
}
