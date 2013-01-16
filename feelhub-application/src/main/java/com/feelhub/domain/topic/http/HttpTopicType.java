package com.feelhub.domain.topic.http;

import com.feelhub.domain.topic.TopicType;

public enum HttpTopicType implements TopicType {

    Article(false),
    Audio(true),
    Data(false),
    Image(true),
    Other(false),
    Video(true),
    Website(false);

    HttpTopicType(final boolean media) {
        this.media = media;
    }

    @Override
    public boolean hasTagUniqueness() {
        return true;
    }

    @Override
    public boolean isMedia() {
        return media;
    }

    @Override
    public boolean isTranslatable() {
        return false;
    }

    private final boolean media;
}
