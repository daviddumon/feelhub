package com.feelhub.application.command.user;

import com.feelhub.application.command.Command;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.domain.user.activation.Activation;
import com.feelhub.domain.user.activation.ActivationFactory;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class CreateUserCommand implements Command<UUID> {
    public CreateUserCommand(String email, String password, String fullname, String language) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.language = language;
    }

    @Override
    public UUID execute() {
        final User user = new UserFactory().createUser(email, password, fullname, language);
        Repositories.users().add(user);
        final Activation activation = new ActivationFactory().createForUser(user);
        Repositories.activation().add(activation);
        return user.getId();
    }

    public final String email;
    public final String password;
    public final String fullname;
    public final String language;
}
