package com.feelhub.domain.topic.usable.web;

import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.usable.UsableTopic;

import java.util.UUID;

public class WebTopic extends UsableTopic {

    //mongolink
    protected WebTopic() {

    }

    @Override
    public TopicType getType() {
        return WebTopicType.valueOf(typeValue);
    }

    public void setType(final WebTopicType type) {
        this.typeValue = type.toString();
    }

    public String getTypeValue() {
        return typeValue;
    }

    public WebTopic(final UUID id, final WebTopicType type) {
        super(id);
        this.typeValue = type.toString();
    }

    private String typeValue;
}
