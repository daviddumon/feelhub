package com.feelhub.domain.topic.http;

import com.feelhub.domain.topic.TopicType;

public enum HttpTopicType implements TopicType {

    Article,
    Audio,
    Data,
    Image,
    Other,
    Video,
    Website;

    @Override
    public boolean hasTagUniqueness() {
        return true;
    }
}
