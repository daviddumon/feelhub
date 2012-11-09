package com.feelhub.web.resources.social;

import com.feelhub.application.UserService;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.restlet.data.Status;
import org.restlet.resource.*;
import org.scribe.model.Token;

public class FacebookResource extends ServerResource {

    @Inject
    public FacebookResource(final FacebookConnector connector, final UserService userService, final AuthenticationManager authenticationManager) {
        this.connector = connector;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Get
    public void facebookReturn() {
        final String facebookCode = getQuery().getFirstValue("code");
        final Token accesToken = connector.getAccesToken(facebookCode);
        final User facebookUser = connector.getUser(accesToken);
        final com.feelhub.domain.user.User newUser = userService.createFromFacebook(facebookUser.getId(), facebookUser.getEmail(), facebookUser.getFirstName(), facebookUser.getLastName(), facebookUser.getLocale());
        authenticationManager.authenticate(AuthRequest.facebook(newUser.getId()));
        setLocationRef(new WebReferenceBuilder(getContext()).buildUri(""));
        setStatus(Status.REDIRECTION_TEMPORARY);
    }

    private final FacebookConnector connector;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
}
