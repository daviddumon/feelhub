package com.feelhub.domain.feeling;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.UUID;

public class Sentiment {

    // Constructor for mapper : do not delete !
    protected Sentiment() {
    }

    public Sentiment(final UUID topicId, final SentimentValue sentimentValue) {
        this.topicId = topicId;
        this.sentimentValue = sentimentValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final Sentiment sentiment = (Sentiment) o;
        return Objects.equal(sentiment.getTopicId(), this.getTopicId()) && Objects.equal(sentiment.getSentimentValue(), this.getSentimentValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTopicId() + getSentimentValue().toString());
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

    public DateTime getCreationDate() {
        return creationDate;
    }

    private UUID topicId;
    private SentimentValue sentimentValue;
    private DateTime creationDate = new DateTime();
}
