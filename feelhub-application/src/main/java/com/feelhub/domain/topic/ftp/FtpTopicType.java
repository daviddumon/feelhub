package com.feelhub.domain.topic.ftp;

import com.feelhub.domain.topic.TopicType;

public enum FtpTopicType implements TopicType {

    Ftp;

    @Override
    public boolean hasTagUniqueness() {
        return true;
    }

    @Override
    public boolean isMedia() {
        return false;
    }
}
