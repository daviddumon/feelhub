package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;
import org.joda.time.DateTime;

public class OpinionPostedEvent implements DomainEvent {

    public OpinionPostedEvent(final Opinion opinion) {
        this.opinion = opinion;
        this.date = new DateTime();
    }

    public Opinion getOpinion() {
        return opinion;
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
        stringBuilder.append("Opinion ");
        stringBuilder.append(opinion.getId());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    private final Opinion opinion;
    private final DateTime date;
}
