package com.steambeat.domain.opinion;

import com.steambeat.domain.DomainEvent;
import com.steambeat.domain.subject.Subject;
import org.joda.time.DateTime;

public class OpinionPostedEvent implements DomainEvent {

    public OpinionPostedEvent(final Subject subject, final Opinion opinion) {
        this.subject = subject;
        this.opinion = opinion;
        this.date = new DateTime();
    }

    public Subject getSubject() {
        return subject;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    private Subject subject;
    private Opinion opinion;
    private DateTime date;
}
