package com.feelhub.application.command.user;

import com.feelhub.domain.user.*;

public class CreateUserFromFacebookCommand extends CreateUserFromSocialNetworkCommand {

    public CreateUserFromFacebookCommand(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        super(id, email, firstName, lastName, language, token);
    }

    @Override
    protected SocialNetwork socialNetwork() {
        return SocialNetwork.FACEBOOK;
    }

    @Override
    protected User create() {
        return new UserFactory().createFromFacebook(id, email, firstName, lastName, language, token);
    }

}
