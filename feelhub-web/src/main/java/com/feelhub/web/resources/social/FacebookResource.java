package com.feelhub.web.resources.social;

import com.feelhub.application.UserService;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.joda.time.DateTime;
import org.joda.time.Days;
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
    public ModelAndView facebookReturn() {
        final String facebookCode = getQuery().getFirstValue("code");
        final Token accesToken = connector.getAccesToken(facebookCode);
        final User facebookUser = connector.getUser(accesToken);
        final com.feelhub.domain.user.User user = userService.findOrCreateForFacebook(facebookUser.getId(), facebookUser.getEmail(),
                facebookUser.getFirstName(), facebookUser.getLastName(), facebookUser.getLocale(), accesToken.getToken());
        authenticationManager.authenticate(AuthRequest.facebook(user.getId()));
        if(isOldUser(user)) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri(""));
            setStatus(Status.REDIRECTION_TEMPORARY);
            return ModelAndView.empty();
        } else {
            return ModelAndView.createNew("social/welcome.ftl");
        }
    }

    private boolean isOldUser(com.feelhub.domain.user.User newUser) {
        return !Days.daysBetween(newUser.getCreationDate(), DateTime.now()).isLessThan(Days.ONE);
    }

    private final FacebookConnector connector;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
}
