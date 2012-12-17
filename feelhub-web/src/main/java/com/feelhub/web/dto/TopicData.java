package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.world.UnusableTopicTypes;
import com.google.common.collect.Lists;
import org.json.JSONObject;

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

        public Builder subtypes(final List<String> subTypes) {
            this.subTypes = subTypes;
            return this;
        }

        public Builder urls(final List<String> urls) {
            this.urls = urls;
            return this;
        }

        private String id = "";
        private final String illustrationLink = "";
        private String name = "";
        private SentimentValue sentimentValue = SentimentValue.none;
        private TopicType type = UnusableTopicTypes.None;
        private List<String> subTypes = Lists.newArrayList();
        private List<String> urls = Lists.newArrayList();
    }

    private TopicData(final Builder builder) {
        this.id = builder.id;
        this.illustrationLink = builder.illustrationLink;
        this.name = builder.name;
        this.sentimentValue = builder.sentimentValue;
        this.type = builder.type.toString();
        this.subTypes = builder.subTypes;
        this.urls = builder.urls;
    }

    public String getId() {
        return id;
    }

    public String getIllustrationLink() {
        return illustrationLink;
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

    public List<String> getSubTypes() {
        return subTypes;
    }

    public List<String> getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    private final String id;
    private final String illustrationLink;
    private final String name;
    private final SentimentValue sentimentValue;
    private final String type;
    private final List<String> subTypes;
    private final List<String> urls;
}
