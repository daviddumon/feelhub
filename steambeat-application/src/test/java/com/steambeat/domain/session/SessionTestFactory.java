package com.steambeat.domain.session;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SessionTestFactory {

    public Session createSessionFor(final User user) {
        final Session session = new Session();
        session.setEmail(user.getEmail());
        session.setToken(UUID.randomUUID());
        Repositories.sessions().add(session);
        return session;
    }
}
