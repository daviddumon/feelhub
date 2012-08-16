package com.steambeat.domain.eventbus;

import com.google.common.eventbus.DeadEvent;

public class SteambeatDeadEvent extends DomainEvent {

    public SteambeatDeadEvent(DeadEvent deadEvent) {
        super();
        this.deadEvent = deadEvent;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Dead event : ");
        stringBuilder.append(deadEvent.getEvent().toString());
        return stringBuilder.toString();
    }

    private DeadEvent deadEvent;
}
