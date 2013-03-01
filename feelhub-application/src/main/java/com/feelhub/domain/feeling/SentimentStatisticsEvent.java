package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;

import static com.google.common.base.Preconditions.*;

public class SentimentStatisticsEvent extends DomainEvent {

    public SentimentStatisticsEvent(final Sentiment sentiment) {
        checkNotNull(sentiment);
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
        stringBuilder.append(sentiment.getTopicId());
        return stringBuilder.toString();
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    private final Sentiment sentiment;
}
