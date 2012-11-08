package com.feelhub.domain.world;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.feeling.Sentiment;

public class WorldStatisticsEvent extends DomainEvent {

    public WorldStatisticsEvent(final Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        stringBuilder.append(sentiment.getSentimentValue());
        stringBuilder.append(" on ");
        stringBuilder.append(sentiment.getReferenceId());
        return stringBuilder.toString();
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    private final Sentiment sentiment;
}
