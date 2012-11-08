package com.feelhub.domain.feeling;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.reference.Reference;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.*;

public class Feeling extends BaseEntity {

    public static class Builder {

        public Feeling build() {
            return new Feeling(this);
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder user(final String userId) {
            this.userId = userId;
            return this;
        }

        public Builder language(final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder sentiments(final List<Sentiment> sentiments) {
            this.sentiments.addAll(sentiments);
            return this;
        }

        private UUID id = null;
        private String text = "";
        private String userId = "";
        private String languageCode = "";
        private final List<Sentiment> sentiments = Lists.newArrayList();
    }

    //do not delete mongolink constructor
    protected Feeling() {
    }

    private Feeling(final Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.languageCode = builder.languageCode;
        this.userId = builder.userId;
        this.sentiments.addAll(builder.sentiments);
        postEventForAllSentiments();
    }

    private void postEventForAllSentiments() {
        for (final Sentiment sentiment : sentiments) {
            DomainEventBus.INSTANCE.post(new SentimentStatisticsEvent(sentiment));
        }
    }

    public Feeling(final String text, final String userId) {
        this(text, userId, UUID.randomUUID());
    }

    public Feeling(final String text, final String userId, final UUID id) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public void addSentiment(final Reference reference, final SentimentValue sentimentValue) {
        final Sentiment sentiment = new Sentiment(reference.getId(), sentimentValue);
        sentiments.add(sentiment);
        reference.setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new SentimentStatisticsEvent(sentiment));
    }

    public void addSentiment(final Sentiment sentiment) {
        sentiments.add(sentiment);
        //sentiment.getReference().setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new SentimentStatisticsEvent(sentiment));
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Sentiment> getSentiments() {
        return sentiments;
    }

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getUserId() {
        return userId;
    }

    private UUID id;
    private String text;
    private final List<Sentiment> sentiments = Lists.newArrayList();
    private String languageCode;
    private String userId;
}
