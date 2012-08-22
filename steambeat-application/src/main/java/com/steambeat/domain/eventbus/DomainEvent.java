package com.steambeat.domain.eventbus;

import org.joda.time.DateTime;

public abstract class DomainEvent implements Comparable<DomainEvent> {

    public DomainEvent() {
        this.date = new DateTime();
    }

    public DateTime getDate() {
        return date;
    }

    abstract public String toString();

    @Override
    public int compareTo(final DomainEvent o) {
        return this.date.getMillis() < o.getDate().getMillis() ? 0 : 1;
    }

    protected DateTime date;
}
