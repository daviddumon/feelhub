package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;

public class OpinionCreatedEvent extends DomainEvent {

    public OpinionCreatedEvent(final Opinion opinion) {
        super();
        this.opinion = opinion;
    }

    public Opinion getOpinion() {
        return opinion;
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
}
