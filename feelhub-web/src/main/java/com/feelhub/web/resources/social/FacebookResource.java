package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.scribe.model.Token;

import java.util.UUID;

public class FacebookResource extends OauthResource {

    @Inject
    public FacebookResource(final FacebookConnector connector, final AuthenticationManager authenticationManager, final CommandBus bus) {
        super(authenticationManager, bus);
        this.connector = connector;
    }

    @Override
    Token accessToken(final String code) {
        return connector.getAccesToken(code);
    }

    @Override
    protected com.feelhub.domain.user.User getOrCreateUser(final Token accesToken) {
        final User facebookUser = connector.getUser(accesToken);
        final CreateUserFromSocialNetworkCommand command = new CreateUserFromFacebookCommand(facebookUser.getId(), facebookUser.getEmail(),
                facebookUser.getFirstName(), facebookUser.getLastName(), facebookUser.getLocale(), accesToken.getToken());
        final UUID userId = Futures.getUnchecked(bus.execute(command));
        return Repositories.users().get(userId);
    }

    private final FacebookConnector connector;
}
