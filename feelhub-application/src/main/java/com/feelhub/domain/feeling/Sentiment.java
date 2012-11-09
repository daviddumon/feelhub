package com.feelhub.domain.feeling;

import java.util.UUID;

public class Sentiment {

    // Constructor for mapper : do not delete !
    protected Sentiment() {
    }

    public Sentiment(final UUID topicId, final SentimentValue sentimentValue) {
        this.topicId = topicId;
        this.sentimentValue = sentimentValue;
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    private UUID topicId;
    private SentimentValue sentimentValue;
}
