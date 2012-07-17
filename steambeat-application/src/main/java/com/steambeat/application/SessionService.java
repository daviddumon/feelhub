package com.steambeat.application;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SessionService {

    public Session getSessionFor(final User user) {
        Session session = lookUpSession(user);
        if (session == null) {
            session = new Session();
            session.setEmail(user.getEmail());
            session.setToken(UUID.randomUUID());
            Repositories.sessions().add(session);
        } else {
            session.renew();
        }
        return session;
    }

    private Session lookUpSession(final User user) {
        return Repositories.sessions().get(user.getEmail());
    }

    public void deleteSessionFor(final User user) {
        final Session session = lookUpSession(user);
        if (session != null) {
            Repositories.sessions().delete(session);
        }
    }
}
