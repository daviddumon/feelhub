package com.steambeat.domain.opinion;

import com.steambeat.domain.eventbus.DomainEvent;

public class JudgmentCreatedEvent extends DomainEvent {

    public JudgmentCreatedEvent(final Judgment judgment) {
        super();
        this.judgment = judgment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Judgment ");
        stringBuilder.append(judgment.getFeeling());
        stringBuilder.append(" on ");
        stringBuilder.append(judgment.getReference().getId());
        return stringBuilder.toString();
    }

    public Judgment getJudgment() {
        return judgment;
    }

    private final Judgment judgment;
}
