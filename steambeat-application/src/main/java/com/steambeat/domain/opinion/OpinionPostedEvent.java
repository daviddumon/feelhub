package com.steambeat.domain.opinion;

import com.steambeat.domain.DomainEvent;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class OpinionPostedEvent implements DomainEvent {

    //todo
    public OpinionPostedEvent(final Subject subject, final Opinion opinion) {
        this.opinion = opinion;
        this.date = new DateTime();
    }

    public OpinionPostedEvent(final Opinion opinion) {
        this.opinion = opinion;
        this.date = new DateTime();
    }

    //todo
    public Subject getSubject() {
        return this.opinion.getSubject();
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
