package com.feelhub.web.resources.social;

import com.feelhub.application.UserService;
import com.feelhub.web.ReferenceBuilder;
import com.feelhub.web.authentification.AuthRequest;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.scribe.model.Token;

public class FacebookResource extends ServerResource {

    @Inject
    public FacebookResource(FacebookConnector connector, UserService userService, AuthenticationManager authenticationManager) {
        this.connector = connector;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

    @Get
    public void facebookReturn() {
        final String facebookCode = getQuery().getFirstValue("code");
        final Token accesToken = connector.getAccesToken(facebookCode);
        final User facebookUser = connector.getUser(accesToken);
		final com.feelhub.domain.user.User newUser = userService.createUser(facebookUser.getEmail(), "", facebookUser.getFirstName() + " " + facebookUser.getLastName(), facebookUser.getLocale());
		authenticationManager.authenticate(AuthRequest.facebook(newUser.getEmail()));
		setLocationRef(new ReferenceBuilder(getContext()).buildUri(""));
		setStatus(Status.REDIRECTION_TEMPORARY);
	}

    private FacebookConnector connector;
	private UserService userService;
	private AuthenticationManager authenticationManager;
}
