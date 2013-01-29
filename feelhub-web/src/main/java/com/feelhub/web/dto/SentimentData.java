package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.topic.*;
import org.json.JSONObject;

import java.util.UUID;

public class SentimentData {

    public static class Builder {

        public SentimentData build() {
            return new SentimentData(this);
        }

        public Builder id(final UUID id) {
            this.id = id.toString();
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
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

        public Builder illustration(final String illustration) {
            this.illustration = illustration;
            return this;
        }

        private String id = "";
        private String illustration = "";
        private String name = "";
        private SentimentValue sentimentValue = SentimentValue.none;
        private TopicType type = UnusableTopicTypes.None;
    }

    private SentimentData(final Builder builder) {
        this.id = builder.id;
        this.illustration = builder.illustration;
        this.name = builder.name;
        this.sentimentValue = builder.sentimentValue;
        this.type = builder.type.toString();
    }

    public String getId() {
        return id;
    }

    public String getIllustration() {
        return illustration;
    }

    public String getName() {
        return name;
    }

    public SentimentValue getSentimentValue() {
        return sentimentValue;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    private final String id;
    private final String illustration;
    private final String name;
    private final SentimentValue sentimentValue;
    private final String type;
}
