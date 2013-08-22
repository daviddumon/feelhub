package com.feelhub.web.dto;

import com.feelhub.domain.feeling.FeelingValue;
import com.google.common.collect.Lists;

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

        private UUID id;
        private UUID userId;
        private UUID topicId;
        private String text = "";
        private String languageCode = "";
        private FeelingValue feelingValue;
    }

    private FeelingData(final Builder builder) {
        this.id = builder.id;
        this.text = Lists.newArrayList(builder.text.split("\r\n"));
        this.languageCode = builder.languageCode;
        this.userId = builder.userId;
        this.topicId = builder.topicId;
        this.feelingValue = builder.feelingValue;
    }

    public UUID getId() {
        return id;
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

    private final UUID id;
    private final UUID userId;
    private final UUID topicId;
    private final FeelingValue feelingValue;
    private final List<String> text;
    private final String languageCode;
}
