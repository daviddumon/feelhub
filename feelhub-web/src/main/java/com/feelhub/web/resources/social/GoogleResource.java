package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.social.*;
import com.feelhub.web.tools.CookieManager;
import com.google.common.util.concurrent.Futures;
import org.scribe.model.Token;

import javax.inject.Inject;
import java.util.UUID;

public class GoogleResource extends OauthResource {


    @Inject
    public GoogleResource(final GoogleConnector connector, final AuthenticationManager authenticationManager, final CommandBus bus, final CookieManager cookieManager) {
        super(authenticationManager, bus, cookieManager);
        this.connector = connector;
    }

    @Override
    Token accessToken(final String code) {
        return connector.getAccesToken(code);
    }

    @Override
    protected User getOrCreateUser(final Token accessToken) {
        final GoogleUser googleUser = connector.getUser(accessToken);
        final CreateUserFromSocialNetworkCommand command = new CreateUserFromGoogleCommand(googleUser.id, googleUser.email,
                googleUser.given_name, googleUser.family_name, googleUser.locale, accessToken.getToken());
        final UUID userId = Futures.getUnchecked(bus.execute(command));
        return Repositories.users().get(userId);
    }


    private final GoogleConnector connector;
}
