package com.feelhub.domain.feeling;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.*;

public class Feeling extends BaseEntity {

    //do not delete mongolink constructor
    protected Feeling() {
    }

    public Feeling(final UUID userId, final UUID topicId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.topicId = topicId;
        this.feelingValue = FeelingValue.neutral;
    }

    //todo delete
    public void addSentiment(final Sentiment sentiment) {
        sentiments.add(sentiment);
        this.setLastModificationDate(new DateTime());
        //DomainEventBus.INSTANCE.post(new SentimentAddedEvent(sentiment));
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    //todo delete
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

    public UUID getTopicId() {
        return topicId;
    }

    public FeelingValue getFeelingValue() {
        return this.feelingValue;
    }

    public void setFeelingValue(final FeelingValue feelingValue) {
        this.feelingValue = feelingValue;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    public void addRelatedTopic(final UUID relatedId) {
        this.relatedTopics.add(relatedId);
    }

    public List<UUID> getRelatedTopics() {
        return this.relatedTopics;
    }

    private UUID id;
    private String text;
    private String languageCode;
    private UUID userId;
    private UUID topicId;
    private FeelingValue feelingValue;
    private final List<UUID> relatedTopics = Lists.newArrayList();
    //todo delete
    private final List<Sentiment> sentiments = Lists.newArrayList();
}
