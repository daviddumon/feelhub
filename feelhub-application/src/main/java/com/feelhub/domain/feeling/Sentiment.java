package com.feelhub.domain.feeling;

import java.util.UUID;

public class Sentiment {

    // Constructor for mapper : do not delete !
    protected Sentiment() {
    }

    public Sentiment(final UUID referenceId, final SentimentValue sentimentValue) {
        this.referenceId = referenceId;
        this.sentimentValue = sentimentValue;
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    private UUID referenceId;
    private SentimentValue sentimentValue;
}
