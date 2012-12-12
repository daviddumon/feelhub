package com.feelhub.domain.tag;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.topic.Topic;
import com.google.common.collect.Lists;

import java.util.*;

public class Tag extends BaseEntity {

    //mongolink constructor do not delete
    public Tag() {
    }

    public Tag(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    public void addTopic(final Topic topic) {
        topicIds.add(topic.getId());
    }

    public List<UUID> getTopicIds() {
        return topicIds;
    }

    private String id;
    private final List<UUID> topicIds = Lists.newArrayList();
}
