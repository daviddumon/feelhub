package com.feelhub.domain.topic.usable.web;

import com.feelhub.domain.topic.TopicType;
import com.feelhub.domain.topic.usable.UsableTopic;
import com.google.common.collect.Lists;

import java.util.*;

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

    public List<String> getUrls() {
        return urls;
    }

    public void addUrl(final String url) {
        urls.add(url);
    }

    private final List<String> urls = Lists.newArrayList();
    private String typeValue;
}
