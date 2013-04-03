package com.feelhub.application.command.user;

import com.feelhub.application.command.Command;
import com.feelhub.domain.user.SocialAuth;
import com.feelhub.domain.user.SocialNetwork;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Optional;

import java.util.UUID;

public abstract class CreateUserFromSocialNetworkCommand implements Command<UUID> {
    public CreateUserFromSocialNetworkCommand(final String id, final String email, final String firstName, final String lastName, final String language, final String token) {
        this.firstName = firstName;
        this.id = id;
        this.token = token;
        this.language = language;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public UUID execute() {
        final User user = Repositories.users().findBySocialNetwork(socialNetwork(), id);
        if (user != null) {
            return user.getId();
        }
        final Optional<User> byEmail = Repositories.users().forEmail(email);
        if (byEmail.isPresent()) {
            byEmail.get().addSocialAuth(new SocialAuth(socialNetwork(), id, token));
            return byEmail.get().getId();
        }
        final User userCreated = create();
        Repositories.users().add(userCreated);
        return userCreated.getId();
    }

    protected abstract SocialNetwork socialNetwork();

    protected abstract User create();

    public final String id;
    public final String email;
    public final String firstName;
    public final String lastName;
    public final String language;
    public final String token;
}
