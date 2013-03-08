package com.feelhub.application.command.session;

import com.feelhub.application.command.Command;
import com.feelhub.domain.session.Session;
import com.feelhub.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.UUID;

public class CreateSessionCommand implements Command<UUID> {

    public CreateSessionCommand(UUID userId, DateTime expirationDate) {
        this.userId = userId;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID execute() {
        final Session session = new Session(expirationDate, Repositories.users().get(userId));
        Repositories.sessions().add(session);
        return session.getId();
    }

    public final UUID userId;
    public final DateTime expirationDate;
}
