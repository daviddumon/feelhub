package com.feelhub.application.command.session;

import com.feelhub.application.command.Command;
import com.feelhub.domain.session.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class AuthenticateCommand implements Command<Boolean> {

    public AuthenticateCommand(final UUID userId, final UUID token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public Boolean execute() {
        try {
            final Session session = lookUpSession(token);
            checkSessionForUser(Repositories.users().get(userId), session);
            checkExpired(session);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Session lookUpSession(final UUID token) {
        return Repositories.sessions().get(token);
    }

    private void checkSessionForUser(final User user, final Session session) {
        if (session.isOwnedBy(user)) {
            throw new SessionException();
        }
    }

    private void checkExpired(final Session session) {
        if (session.isExpired()) {
            throw new SessionException();
        }
    }

    public final UUID userId;
    public final UUID token;
}
