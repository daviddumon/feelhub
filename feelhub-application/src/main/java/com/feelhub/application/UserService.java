package com.feelhub.application;

import com.feelhub.domain.user.*;
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
        final User user = Repositories.users().findBySocialNetwork(SocialNetwork.FACEBOOK, id);
        if (user != null) {
            return user;
        }
        final User byEmail = Repositories.users().forEmail(email);
        if (byEmail != null) {
            byEmail.addSocialAuth(new SocialAuth(SocialNetwork.FACEBOOK, id, token));
            return byEmail;
        }
        return createFromFacebook(id, email, firstName, lastName, language, token);
    }

    private User createFromFacebook(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        final User user = userFactory.createFromFacebook(id, email, firstName, lastName, language, token);
        Repositories.users().add(user);
        return user;
    }

    private final UserFactory userFactory;
}
