package com.steambeat.application;

import com.steambeat.domain.session.*;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

import java.util.UUID;

public class SessionService {

    public Session createSession(final User user, final DateTime expirationDate) {
        final Session session = new Session(expirationDate);
        session.setEmail(user.getEmail());
        Repositories.sessions().add(session);
        return session;
    }

    public boolean authentificate(final User user, final UUID token) {
        try {
            final Session session = lookUpSession(token);
            checkSessionForUser(user, session);
            checkExpired(session);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void checkSessionForUser(final User user, final Session session) {
        if (!session.getEmail().equalsIgnoreCase(user.getEmail())) {
            throw new SessionException();
        }
    }

    private void checkExpired(final Session session) {
        if (session.isExpired()) {
            throw new SessionException();
        }
    }

    private Session lookUpSession(final UUID token) {
        return Repositories.sessions().get(token);
    }

    public void deleteSession(final UUID token) {
        final Session session = lookUpSession(token);
        if (session != null) {
            Repositories.sessions().delete(session);
        }
    }
}
