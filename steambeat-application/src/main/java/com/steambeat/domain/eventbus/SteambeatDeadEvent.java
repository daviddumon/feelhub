package com.steambeat.domain.eventbus;

import com.google.common.eventbus.DeadEvent;
import org.joda.time.DateTime;

public class SteambeatDeadEvent implements DomainEvent {

    public SteambeatDeadEvent(DeadEvent deadEvent) {
        this.deadEvent = deadEvent;
        this.date = new DateTime();
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
        stringBuilder.append("Dead event : ");
        stringBuilder.append(deadEvent.getEvent().toString());
        return stringBuilder.toString();
    }

    private DateTime date;
    private DeadEvent deadEvent;
}
