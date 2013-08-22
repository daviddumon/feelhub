package com.feelhub.web.dto;

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

        public Builder text(final String text) {
            this.text = text;
            return this;
        }

        public Builder languageCode(final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder sentimentDatas(final List<SentimentData> sentimentDatas) {
            this.sentimentDatas = sentimentDatas;
            return this;
        }

        public Builder sentimentDatas(final List<SentimentData> sentimentDatas, final UUID contextId) {
            for (final SentimentData sentimentData : sentimentDatas) {
                if (!sentimentData.getId().equals(contextId.toString())) {
                    this.sentimentDatas.add(sentimentData);
                } else {
                    this.feelingSentimentValue = sentimentData.getSentimentValue();
                }
            }
            return this;
        }

        private UUID id;
        private String text = "";
        private UUID userId;
        private String languageCode = "";
        private List<SentimentData> sentimentDatas = Lists.newArrayList();
        private SentimentValue feelingSentimentValue = null;
    }

    private FeelingData(final Builder builder) {
        this.id = builder.id;
        this.text = Lists.newArrayList(builder.text.split("\r\n"));
        this.languageCode = builder.languageCode;
        this.userId = builder.userId;
        this.sentimentDatas = builder.sentimentDatas;
        this.feelingSentimentValue = builder.feelingSentimentValue;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<String> getText() {
        return text;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public List<SentimentData> getSentimentDatas() {
        return sentimentDatas;
    }

    public SentimentValue getFeelingSentimentValue() {
        return feelingSentimentValue;
    }

    private final UUID id;
    private final UUID userId;
    private final List<String> text;
    private final String languageCode;
    private final List<SentimentData> sentimentDatas;
    private final SentimentValue feelingSentimentValue;
}
