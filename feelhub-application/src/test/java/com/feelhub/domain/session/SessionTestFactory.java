package com.feelhub.domain.session;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import org.joda.time.DateTime;

public class SessionTestFactory {

    public Session createSessionFor(final User user) {
        final Session session = new Session(new DateTime().plusHours(1), user);
        Repositories.sessions().add(session);
        return session;
    }
}
