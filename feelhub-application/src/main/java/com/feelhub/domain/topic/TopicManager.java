package com.feelhub.domain.topic;

import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TopicManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            if (!oldTopicId.equals(topicPatch.getNewTopicId())) {
                final Topic oldTopic = Repositories.topics().get(oldTopicId);
                oldTopic.setActive(false);
                oldTopic.setCurrentTopicId(topicPatch.getNewTopicId());
            }
        }
    }
}
