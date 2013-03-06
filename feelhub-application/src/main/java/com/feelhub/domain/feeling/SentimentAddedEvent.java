package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.*;

public class SentimentAddedEvent extends DomainEvent {

    public SentimentAddedEvent(final Sentiment sentiment) {
        checkNotNull(sentiment);
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Sentiment value", sentiment.getSentimentValue()).add("Topic Id", sentiment.getTopicId()).toString();
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public final Sentiment sentiment;
}
