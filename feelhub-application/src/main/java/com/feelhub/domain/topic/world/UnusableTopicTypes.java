package com.feelhub.domain.topic.world;

import com.feelhub.domain.topic.TopicType;

public enum UnusableTopicTypes implements TopicType {

    None;

    @Override
    public boolean hasTagUniqueness() {
        return false;
    }
}
