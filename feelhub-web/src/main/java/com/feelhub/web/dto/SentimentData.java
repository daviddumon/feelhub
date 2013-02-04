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

        public Builder thumbnailLarge(final String thumbnailLarge) {
            this.thumbnailLarge = thumbnailLarge;
            return this;
        }

        public Builder thumbnailMedium(final String thumbnailMedium) {
            this.thumbnailMedium = thumbnailMedium;
            return this;
        }

        public Builder thumbnailSmall(final String thumbnailSmall) {
            this.thumbnailSmall = thumbnailSmall;
            return this;
        }

        private String id = "";
        private String thumbnailLarge = "";
        private String thumbnailMedium = "";
        private String thumbnailSmall = "";
        private String name = "";
        private SentimentValue sentimentValue = SentimentValue.none;
        private TopicType type = UnusableTopicTypes.None;
    }

    private SentimentData(final Builder builder) {
        this.id = builder.id;
        this.thumbnailLarge = builder.thumbnailLarge;
        this.thumbnailMedium = builder.thumbnailMedium;
        this.thumbnailSmall = builder.thumbnailSmall;
        this.name = builder.name;
        this.sentimentValue = builder.sentimentValue;
        this.type = builder.type.toString();
    }

    public String getId() {
        return id;
    }

    public String getThumbnailLarge() {
        return thumbnailLarge;
    }

    public String getThumbnailMedium() {
        return thumbnailMedium;
    }

    public String getThumbnailSmall() {
        return thumbnailSmall;
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
    private final String thumbnailLarge;
    private final String thumbnailMedium;
    private final String thumbnailSmall;
    private final String name;
    private final SentimentValue sentimentValue;
    private final String type;
}
