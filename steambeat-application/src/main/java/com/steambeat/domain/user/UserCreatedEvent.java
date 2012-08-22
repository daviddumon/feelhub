package com.steambeat.domain.user;

import com.steambeat.domain.eventbus.DomainEvent;

public class UserCreatedEvent extends DomainEvent {

    public UserCreatedEvent(final User user) {
        super();
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("UserCreatedEvent ");
        stringBuilder.append(user.toString());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public User getUser() {
        return user;
    }

    private User user;
}
