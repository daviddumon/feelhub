package com.steambeat.domain.opinion;

import com.steambeat.domain.DomainEvent;
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

    private final Opinion opinion;
    private final DateTime date;
}
