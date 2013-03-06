package com.feelhub.domain.feeling;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.*;

public class Feeling extends BaseEntity {

    //do not delete mongolink constructor
    protected Feeling() {
    }

    public Feeling(final UUID id, final String text, final UUID userId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public void addSentiment(final Sentiment sentiment) {
        sentiments.add(sentiment);
        this.setLastModificationDate(new DateTime());
        DomainEventBus.INSTANCE.post(new SentimentAddedEvent(sentiment));
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

    public UUID getUserId() {
        return userId;
    }

    private UUID id;
    private String text;
    private String languageCode;
    private UUID userId;
    private final List<Sentiment> sentiments = Lists.newArrayList();
}
