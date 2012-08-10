package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;
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

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Judgment ");
        stringBuilder.append(judgment.getFeeling());
        stringBuilder.append(" on ");
        stringBuilder.append(judgment.getTopic().getId());
        return stringBuilder.toString();
    }

    public Judgment getJudgment() {
        return judgment;
    }

    private final DateTime date;
    private final Judgment judgment;
}
