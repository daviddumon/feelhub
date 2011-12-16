package com.steambeat.domain.subject;

import com.steambeat.domain.*;
import com.steambeat.domain.opinion.*;
import org.joda.time.DateTime;

public abstract class Subject extends BaseEntity {

    protected Subject() {

    }

    protected Subject(final String id) {
        this.creationDate = new DateTime();
        this.id = id;
    }

    public Judgment createJudgment(final Feeling feeling) {
        final Judgment judgment = new Judgment(this.getId(), feeling);
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(judgment));
        return judgment;
    }

    //todo
    public Opinion createOpinion(final String text, final Feeling feeling) {
        final Opinion opinion = new Opinion(text, feeling, this);
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
