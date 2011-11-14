package com.steambeat.domain.subject;

import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import org.joda.time.DateTime;

public abstract class Subject extends BaseEntity {

    protected Subject() {

    }

    protected Subject(String id) {
        this.creationDate = new DateTime();
        this.id = id;
    }

    public Opinion createOpinion(final String value, final Feeling feeling) {
        final Opinion opinion = new Opinion(value, feeling, this);
        DomainEventBus.INSTANCE.spread(new OpinionPostedEvent(this, opinion));
        return opinion;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String getId() {
        return id;
    }

    private DateTime creationDate;
    private String id;
}
