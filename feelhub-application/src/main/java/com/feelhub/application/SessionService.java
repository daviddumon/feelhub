package com.feelhub.application;

import com.feelhub.domain.session.Session;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class SessionService {

    public void deleteSession(final UUID token) {
        final Session session = lookUpSession(token);
        if (session != null) {
            Repositories.sessions().delete(session);
        }
    }

    private Session lookUpSession(final UUID token) {
        return Repositories.sessions().get(token);
    }
}
