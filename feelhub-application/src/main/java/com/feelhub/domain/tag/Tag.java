package com.feelhub.domain.tag;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.topic.Topic;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.*;

public class Tag extends BaseEntity {

    //mongolink constructor do not delete
    public Tag() {
    }

    public Tag(final String value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Tag tag = (Tag) o;
        return Objects.equal(tag.value, value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public void addTopic(final Topic topic) {
        topicIds.add(topic.getId());
    }

    public String getValue() {
        return value;
    }

    public List<UUID> getTopicIds() {
        return topicIds;
    }

    private String value;
    private final List<UUID> topicIds = Lists.newArrayList();
}
