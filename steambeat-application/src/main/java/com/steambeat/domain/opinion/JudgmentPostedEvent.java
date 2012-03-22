package com.steambeat.domain.opinion;

import com.steambeat.domain.DomainEvent;
import org.joda.time.DateTime;

public class JudgmentPostedEvent implements DomainEvent {

    public JudgmentPostedEvent(final Judgment judgment) {
        this.date = new DateTime();
        this.judgment = judgment;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public Judgment getJudgment() {
        return judgment;
    }

    private final DateTime date;
    private final Judgment judgment;
}
