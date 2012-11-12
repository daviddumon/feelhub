package com.feelhub.application;

import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.domain.user.UserIds;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class UserService {

    @Inject
    public UserService(final UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public User createUser(final String email, final String password, final String fullname, final String language) {
        final User user = userFactory.createUser(email, password, fullname, language);
        Repositories.users().add(user);
        return user;
    }

    public User findOrCreateForFacebook(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        if (Repositories.users().exists(UserIds.facebook(id))) {
            return Repositories.users().get(UserIds.facebook(id));
        }
        final User user = userFactory.createFromFacebook(id, email, firstName, lastName, language, token);
        Repositories.users().add(user);
        return user;
    }

    private final UserFactory userFactory;
}
