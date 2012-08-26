package com.steambeat.domain.eventbus;

import com.google.common.eventbus.DeadEvent;

public class SteambeatDeadEvent extends DomainEvent {

    public SteambeatDeadEvent(final DeadEvent deadEvent) {
        super();
        this.deadEvent = deadEvent;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("SteambeatDeadEvent [ ");
        stringBuilder.append(deadEvent.getEvent().toString());
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    private final DeadEvent deadEvent;
}
