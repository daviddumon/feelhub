package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.DomainEvent;

public class UserCreatedEvent extends DomainEvent {

    public UserCreatedEvent(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        stringBuilder.append(user.toString());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public final User user;
}
