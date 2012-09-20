package com.steambeat.application;

import com.steambeat.domain.session.Session;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import org.restlet.data.Cookie;

import java.util.UUID;

public class SessionService {

    public Session getOrCreateSessionForUser(final User user) {
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

    public boolean validateCookieForUser(final Cookie cookie, final User user) {
        if (cookie != null) {
            final Session session = lookUpSession(user);
            if (session == null) {
                return false;
            } else {
                return session.getToken().toString().equalsIgnoreCase(cookie.getValue());
            }
        } else {
            return false;
        }
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
