package com.feelhub.domain.topic.usable.web;

import com.feelhub.domain.topic.TopicType;

public enum WebTopicType implements TopicType {

    Article,
    Audio,
    File,
    Ftp,
    Image,
    Other,
    Video,
    Website;

    @Override
    public boolean hasTagUniqueness() {
        return true;
    }
}
