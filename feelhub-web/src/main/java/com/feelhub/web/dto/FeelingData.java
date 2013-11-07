package com.feelhub.web.dto;

import com.feelhub.domain.feeling.FeelingValue;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.*;

public class FeelingData {

    public static class Builder {

        public FeelingData build() {
            return new FeelingData(this);
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder userId(final UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder topicId(final UUID topicId) {
            this.topicId = topicId;
            return this;
        }

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder feelingValue(final FeelingValue feelingValue) {
            this.feelingValue = feelingValue;
            return this;
        }

        public Builder languageCode(final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder creationDate(final DateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder force(final int force) {
            this.force = force;
            return this;
        }

        private UUID id;

        private UUID userId;
        private UUID topicId;
        private String text = "";
        private String languageCode = "";
        private FeelingValue feelingValue;
        private DateTime creationDate = new DateTime();
        private int force = 1;
    }

    private FeelingData(final Builder builder) {
        this.id = builder.id;
        this.text = Lists.newArrayList(builder.text.split("\n"));
        this.languageCode = builder.languageCode;
        this.userId = builder.userId;
        this.topicId = builder.topicId;
        this.feelingValue = builder.feelingValue;
        this.creationDate = getIntervalFrom(builder.creationDate);
        this.force = builder.force;
    }

    private String getIntervalFrom(final DateTime creationDate) {
        final PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(creationDate.toDate());
    }

    public UUID getId() {
        return id;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public FeelingValue getFeelingValue() {
        return feelingValue;
    }

    public List<String> getText() {
        return text;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public int getForce() {
        return force;
    }

    private final UUID id;
    private final UUID userId;
    private final UUID topicId;
    private final FeelingValue feelingValue;
    private final List<String> text;
    private final String languageCode;
    private final String creationDate;
    private final int force;
}
