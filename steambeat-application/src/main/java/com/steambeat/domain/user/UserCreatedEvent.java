package com.steambeat.domain.user;

import com.steambeat.domain.eventbus.DomainEvent;
import org.joda.time.DateTime;

public class UserCreatedEvent implements DomainEvent {

    public UserCreatedEvent(final User user) {
        this.date = new DateTime();
        this.user = user;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("User ");
        stringBuilder.append(user.toString());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public User getUser() {
        return user;
    }

    private User user;
    private DateTime date;
}
