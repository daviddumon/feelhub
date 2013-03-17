package com.feelhub.domain.session;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.User;
import org.joda.time.DateTime;

public class SessionFactory {
    public Session create(User user, DateTime expirationDate) {
        Session result = new Session(expirationDate, user);
        DomainEventBus.INSTANCE.post(new SessionCreatedEvent(result.getToken(), user.getId()));
        return result;
    }
}
