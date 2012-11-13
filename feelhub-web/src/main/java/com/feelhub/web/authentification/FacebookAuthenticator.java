package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

public class FacebookAuthenticator implements Authenticator {
    @Override
    public User authenticate(final AuthRequest authRequest) {
        return Repositories.users().get(authRequest.getUserId());
    }
}
