package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.topic.TopicType;
import com.google.common.collect.Lists;

import java.util.*;

public class TopicData {

    public static class Builder {

        public TopicData build() {
            return new TopicData(this);
        }

        public Builder id(final UUID id) {
            this.id = id.toString();
            return this;
        }

        public Builder illustration(final Illustration illustration) {
            this.illustrationLink = illustration.getLink();
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder sentimentValue(final SentimentValue sentimentValue) {
            this.sentimentValue = sentimentValue;
            return this;
        }

        public Builder type(final TopicType type) {
            this.type = type;
            return this;
        }

        public Builder subtypes(final List<String> subTypes) {
            this.subTypes = subTypes;
            return this;
        }

        public Builder urls(final List<String> urls) {
            this.urls = urls;
            return this;
        }

        private String id = "";
        private String illustrationLink = "";
        private String description = "";
        private SentimentValue sentimentValue = SentimentValue.none;
        private TopicType type;
        private List<String> subTypes = Lists.newArrayList();
        private List<String> urls = Lists.newArrayList();
    }

    private TopicData(final Builder builder) {
        this.id = builder.id;
        this.illustrationLink = builder.illustrationLink;
        this.description = builder.description;
        this.sentimentValue = builder.sentimentValue;
        this.type = builder.type;
        this.subTypes = builder.subTypes;
        this.urls = builder.urls;
    }

    public String getId() {
        return id;
    }

    public String getIllustrationLink() {
        return illustrationLink;
    }

    public String getDescription() {
        return description;
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    public TopicType getType() {
        return type;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public List<String> getUrls() {
        return urls;
    }

    private final String id;
    private final String illustrationLink;
    private final String description;
    private final SentimentValue sentimentValue;
    private final TopicType type;
    private final List<String> subTypes;
    private final List<String> urls;
}
