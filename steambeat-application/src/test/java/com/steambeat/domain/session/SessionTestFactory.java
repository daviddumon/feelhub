package com.steambeat.domain.session;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import org.joda.time.DateTime;

public class SessionTestFactory {

    public Session createSessionFor(final User user) {
        final Session session = new Session(new DateTime().plusHours(1));
        session.setEmail(user.getEmail());
        Repositories.sessions().add(session);
        return session;
    }
}
