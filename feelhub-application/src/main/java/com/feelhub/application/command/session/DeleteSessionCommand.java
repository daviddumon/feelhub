package com.feelhub.application.command.session;

import com.feelhub.application.command.Command;
import com.feelhub.domain.session.Session;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class DeleteSessionCommand implements Command<Object> {

    public DeleteSessionCommand(final UUID token) {
        this.token = token;
    }

    @Override
    public Object execute() {
        final Session session = lookUpSession(token);
        if (session != null) {
            Repositories.sessions().delete(session);
        }
        return null;
    }

    private Session lookUpSession(final UUID token) {
        return Repositories.sessions().get(token);
    }

    public final UUID token;
}
