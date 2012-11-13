package com.feelhub.web.authentification;

import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;

public class FeelhubAuthenticator implements Authenticator {
    @Override
    public User authenticate(final AuthRequest authRequest) {
        final User user = getUser(authRequest.getUserId());
        checkUser(user);
        checkPassword(user, authRequest.getPassword());
        return user;
    }

    public User getUser(final String id) {
        final User user = Repositories.users().get(id);
        if (user == null) {
            throw new BadUserException("This user does not exist!");
        }
        return user;
    }

    private void checkUser(final User user) {
        if (!user.isActive()) {
            throw new BadUserException("Not yet activated !");
        }
    }

    private void checkPassword(final User user, final String password) {
        if (!user.checkPassword(password)) {
            throw new BadPasswordException();
        }
    }
}
