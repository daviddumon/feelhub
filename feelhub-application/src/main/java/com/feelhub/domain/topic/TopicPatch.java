package com.feelhub.domain.topic;

import com.google.common.collect.Lists;

import java.util.*;

public class TopicPatch {

    public TopicPatch(final UUID newTopicId) {
        this.newTopicId = newTopicId;
    }

    public UUID getNewTopicId() {
        return newTopicId;
    }

    public List<UUID> getOldTopicIds() {
        return oldTopicIds;
    }

    public void addOldTopicId(final UUID oldTopicId) {
        this.oldTopicIds.add(oldTopicId);
    }

    final UUID newTopicId;
    final List<UUID> oldTopicIds = Lists.newArrayList();
}
