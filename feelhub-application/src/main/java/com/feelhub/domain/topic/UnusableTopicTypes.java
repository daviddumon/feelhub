package com.feelhub.domain.topic;

public enum UnusableTopicTypes implements TopicType {

    None;

    @Override
    public boolean hasTagUniqueness() {
        return false;
    }

    @Override
    public boolean isMedia() {
        return false;
    }
}
