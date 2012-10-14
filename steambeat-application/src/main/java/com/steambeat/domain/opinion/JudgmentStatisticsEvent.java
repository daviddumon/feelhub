package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;

public class JudgmentStatisticsEvent extends DomainEvent {

    public JudgmentStatisticsEvent(final Judgment judgment) {
        super();
        this.judgment = judgment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("JudgmentCreatedEvent ");
        stringBuilder.append(judgment.getFeeling());
        stringBuilder.append(" on ");
        stringBuilder.append(judgment.getReferenceId());
        return stringBuilder.toString();
    }

    public Judgment getJudgment() {
        return judgment;
    }

    private final Judgment judgment;
}