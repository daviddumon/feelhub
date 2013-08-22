package com.feelhub.web.dto;

import com.feelhub.domain.topic.*;
import org.json.JSONObject;

import java.util.UUID;

public class SentimentData {

    public static class Builder {

        public SentimentData build() {
            return new SentimentData(this);
        }

        public Builder id(final UUID id) {
            if (id != null) {
                this.id = id.toString();
            }
            return this;
        }

        public Builder name(final String name) {
            if (name != null) {
                this.name = name;
            }
            return this;
        }

        public Builder sentimentValue(final SentimentValue sentimentValue) {
            if (sentimentValue != null) {
                this.sentimentValue = sentimentValue;
            }
            return this;
        }

        public Builder type(final TopicType type) {
            if (type != null) {
                this.type = type;
            }
            return this;
        }

        public Builder thumbnail(final String thumbnail) {
            if (thumbnail != null) {
                this.thumbnail = thumbnail;
            }
            return this;
        }

        private String id = "";
        private String name = "";
        private SentimentValue sentimentValue = SentimentValue.neutral;
        private TopicType type = UnusableTopicTypes.None;
        private String thumbnail = "";
    }

    private SentimentData(final Builder builder) {
        this.id = builder.id;
        this.thumbnail = builder.thumbnail;
        this.name = builder.name;
        this.sentimentValue = builder.sentimentValue;
        this.type = builder.type.toString();
    }

    public String getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
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
    private final String thumbnail;
    private final String name;
    private final SentimentValue sentimentValue;
    private final String type;
}
