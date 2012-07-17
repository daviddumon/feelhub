package com.steambeat.test.testFactories;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SessionFactoryForTest {

    public Session createSessionFor(final User user) {
        Session session = new Session();
        session.setEmail(user.getEmail());
        session.setToken(UUID.randomUUID());
        Repositories.sessions().add(session);
        return session;
    }
}
