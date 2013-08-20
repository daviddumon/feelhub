package com.feelhub.domain.topic.world;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.feeling.Feeling;

public class WorldStatisticsEvent extends DomainEvent {

    public WorldStatisticsEvent(final Feeling feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        stringBuilder.append(feeling.getFeelingValue());
        stringBuilder.append(" on ");
        stringBuilder.append(feeling.getTopicId());
        return stringBuilder.toString();
    }

    public Feeling getFeeling() {
        return this.feeling;
    }

    private final Feeling feeling;
}
