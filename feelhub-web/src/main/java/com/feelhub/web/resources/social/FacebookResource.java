package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.CreateUserFromFacebookCommand;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.joda.time.*;
import org.restlet.data.Status;
import org.restlet.resource.*;
import org.scribe.model.Token;

import java.util.UUID;

public class FacebookResource extends ServerResource {

    @Inject
    public FacebookResource(final FacebookConnector connector, final AuthenticationManager authenticationManager, final CommandBus bus) {
        this.connector = connector;
        this.authenticationManager = authenticationManager;
        this.bus = bus;
    }

    @Get
    public void facebookReturn() {
        final String facebookCode = getQuery().getFirstValue("code");
        final Token accesToken = connector.getAccesToken(facebookCode);
        final User facebookUser = connector.getUser(accesToken);
        final com.feelhub.domain.user.User user = getOrCreateUser(accesToken, facebookUser);
        authenticationManager.authenticate(AuthRequest.facebook(user.getId().toString()));
        setStatus(Status.REDIRECTION_TEMPORARY);
        if (isOldUser(user)) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri(""));
        } else {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/social/welcome"));
        }
    }

    private com.feelhub.domain.user.User getOrCreateUser(Token accesToken, User facebookUser) {
        CreateUserFromFacebookCommand command = new CreateUserFromFacebookCommand(facebookUser.getId(), facebookUser.getEmail(),
                facebookUser.getFirstName(), facebookUser.getLastName(), facebookUser.getLocale(), accesToken.getToken());
        UUID userId = Futures.getUnchecked(bus.execute(command));
        return Repositories.users().get(userId);
    }

    private boolean isOldUser(final com.feelhub.domain.user.User newUser) {
        return !Days.daysBetween(newUser.getCreationDate(), DateTime.now()).isLessThan(Days.ONE);
    }

    private final FacebookConnector connector;
    private final AuthenticationManager authenticationManager;
    private final CommandBus bus;
}
