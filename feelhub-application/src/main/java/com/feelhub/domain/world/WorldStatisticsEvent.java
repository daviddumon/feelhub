package com.feelhub.domain.world;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.opinion.Judgment;

public class WorldStatisticsEvent extends DomainEvent {

    public WorldStatisticsEvent(final Judgment judgment) {
        this.judgment = judgment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
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
