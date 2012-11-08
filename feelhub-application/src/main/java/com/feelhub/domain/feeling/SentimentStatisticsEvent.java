package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;

public class SentimentStatisticsEvent extends DomainEvent {

    public SentimentStatisticsEvent(final Sentiment sentiment) {
        super();
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
