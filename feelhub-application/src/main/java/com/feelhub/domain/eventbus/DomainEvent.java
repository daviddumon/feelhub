package com.feelhub.domain.eventbus;

import org.joda.time.DateTime;

public abstract class DomainEvent implements Comparable<DomainEvent> {

    abstract public String toString();

    @Override
    public int compareTo(final DomainEvent o) {
        return this.date.getMillis() < o.date.getMillis() ? 0 : 1;
    }

    public final DateTime date = new DateTime();
}
