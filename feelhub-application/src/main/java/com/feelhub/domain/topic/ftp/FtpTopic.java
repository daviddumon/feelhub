package com.feelhub.domain.topic.ftp;

import com.feelhub.domain.topic.*;

import java.util.UUID;

public class FtpTopic extends Topic {

    //mongolink
    protected FtpTopic() {

    }

    public FtpTopic(final UUID id) {
        this.id = id;
        this.currentId = id;
    }

    @Override
    public TopicType getType() {
        return FtpTopicType.Ftp;
    }

    @Override
    public String getTypeValue() {
        return FtpTopicType.Ftp.toString();
    }
}
