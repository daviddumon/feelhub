package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.DomainEvent;

public class UserConfirmationMailEvent extends DomainEvent {

    public UserConfirmationMailEvent(final User user) {
        super();
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

    public User getUser() {
        return user;
    }

    private final User user;
}
