package com.feelhub.web.filter;

import com.feelhub.web.authentification.AuthenticationManager;
import com.google.inject.Inject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;

public class IdentityFilter extends Filter {

    @Inject
    public IdentityFilter(final AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

    @Override
    protected int beforeHandle(final Request request, final Response response) {
		authenticationManager.initRequest();
        return CONTINUE;
    }

	private AuthenticationManager authenticationManager;

}
