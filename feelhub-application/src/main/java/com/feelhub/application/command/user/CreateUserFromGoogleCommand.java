package com.feelhub.application.command.user;

import com.feelhub.domain.user.SocialNetwork;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;

public class CreateUserFromGoogleCommand extends CreateUserFromSocialNetworkCommand {
    public CreateUserFromGoogleCommand(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        super(id, email, firstName, lastName, language, token);
    }

    @Override
    protected SocialNetwork socialNetwork() {
        return SocialNetwork.GOOGLE;
    }

    @Override
    protected User create() {
        return new UserFactory().createFromGoogle(id, email, firstName, lastName, language, token);
    }
}
