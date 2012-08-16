package com.steambeat.domain.eventbus;

import org.joda.time.DateTime;

public abstract class DomainEvent {

    public DomainEvent() {
        this.date = new DateTime();
    }

    public DateTime getDate() {
        return date;
    }

    abstract public String toString();

    protected DateTime date;
}
