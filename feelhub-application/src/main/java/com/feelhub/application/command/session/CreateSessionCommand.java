package com.feelhub.application.command.session;

import com.feelhub.application.command.Command;
import com.feelhub.domain.session.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.UUID;

public class CreateSessionCommand implements Command<UUID> {

    public CreateSessionCommand(final UUID userId, final DateTime expirationDate) {
        this.userId = userId;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID execute() {
        final User user = Repositories.users().get(userId);
        final Session session = new SessionFactory().create(user, expirationDate);
        Repositories.sessions().add(session);
        return session.getId();
    }

    public final UUID userId;
    public final DateTime expirationDate;
}
