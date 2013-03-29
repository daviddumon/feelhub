package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

public class SocialNetworkAuthenticator implements Authenticator {
    @Override
    public User authenticate(final AuthRequest authRequest) {
        return Repositories.users().get(authRequest.getUserId());
    }
}
