package com.feelhub.application.command.user;

import com.feelhub.application.command.Command;
import com.feelhub.domain.user.SocialAuth;
import com.feelhub.domain.user.SocialNetwork;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Optional;

import java.util.UUID;

public class CreateUserFromFacebookCommand implements Command<UUID>{

    public CreateUserFromFacebookCommand(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.token = token;
    }

    @Override
    public UUID execute() {
        final User user = Repositories.users().findBySocialNetwork(SocialNetwork.FACEBOOK, id);
        if (user != null) {
            return user.getId();
        }
        final Optional<User> byEmail = Repositories.users().forEmail(email);
        if (byEmail.isPresent()) {
            byEmail.get().addSocialAuth(new SocialAuth(SocialNetwork.FACEBOOK, id, token));
            return byEmail.get().getId();
        }
        return createFromFacebook(id, email, firstName, lastName, language, token).getId();
    }

    private User createFromFacebook(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        final User user = new UserFactory().createFromFacebook(id, email, firstName, lastName, language, token);
        Repositories.users().add(user);
        return user;
    }

    public final String id;
    public final String email;
    public final String firstName;
    public final String lastName;
    public final String language;
    public final String token;
}
