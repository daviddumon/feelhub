package com.feelhub.domain.topic.usable.web;

import com.feelhub.domain.topic.usable.UsableTopic;
import com.google.common.collect.Lists;

import java.util.*;

public class WebTopic extends UsableTopic {

    //mongolink
    protected WebTopic() {

    }

    public WebTopic(final UUID id, final WebTopicType type) {
        super(id, type);
    }

    public List<String> getUrls() {
        return urls;
    }

    public void addUrl(final String url) {
        urls.add(url);
    }

    private final List<String> urls = Lists.newArrayList();
}
