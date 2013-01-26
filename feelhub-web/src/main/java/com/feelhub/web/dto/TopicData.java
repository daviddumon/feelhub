package com.feelhub.web.dto;

import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.UnusableTopicTypes;
import com.feelhub.domain.topic.http.uri.Uri;
import com.google.common.collect.Lists;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class TopicData {

    public static class Builder {

        public TopicData build() {
            return new TopicData(this);
        }

        public Builder id(final UUID id) {
            this.id = id.toString();
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
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

        public Builder uris(final List<Uri> uris) {
            for (Uri uri : uris) {
                this.uris.add(uri.toString());
            }
            return this;
        }

        public Builder illustration(final String illustration) {
            this.illustration = illustration;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder topicSentimentScore(final int topicSentimentScore) {
            this.topicSentimentScore = topicSentimentScore;
            return this;
        }

        private String id = "";
        private String illustration = "";
        private String name = "";
        private TopicType type = UnusableTopicTypes.None;
        private List<String> subTypes = Lists.newArrayList();
        private List<String> uris = Lists.newArrayList();
        private String description = "";
        private int topicSentimentScore;
    }

    private TopicData(final Builder builder) {
        this.id = builder.id;
        this.illustration = builder.illustration;
        this.name = builder.name;
        this.type = builder.type.toString();
        this.subTypes = builder.subTypes;
        this.uris = builder.uris;
        this.description = builder.description;
        this.topicSentimentScore = builder.topicSentimentScore;
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

    public String getType() {
        return type;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public List<String> getUris() {
        return uris;
    }

    public String getDescription() {
        return description;
    }

    public int getTopicSentimentScore() {
        return topicSentimentScore;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    private final String id;
    private final String illustration;
    private final String name;
    private final String type;
    private final List<String> subTypes;
    private final List<String> uris;
    private final String description;
    private final int topicSentimentScore;
}
