package com.feelhub.domain.feeling;

import com.feelhub.domain.BaseEntity;
import com.google.common.collect.Lists;

import java.util.*;

public class Feeling extends BaseEntity {

    //do not delete mongolink constructor
    protected Feeling() {
    }

    public Feeling(final UUID userId, final UUID topicId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.topicId = topicId;
        this.feelingValue = FeelingValue.bored;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
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

    public int getForce() {
        return force;
    }

    public void setForce(final int force) {
        this.force = force;
    }

    private UUID id;
    private String text;
    private String languageCode;
    private UUID userId;
    private UUID topicId;
    private FeelingValue feelingValue;
    private final List<UUID> relatedTopics = Lists.newArrayList();
    private int force = 1;
}
