package com.feelhub.domain.tag;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class TagManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Tag> tags = Repositories.tags().forTopicId(oldTopicId);
            for (final Tag tag : tags) {
                final List<UUID> topicIds = tag.getTopicIds();
                for (int i = 0; i < topicIds.size(); i++) {
                    if (topicIds.get(i).equals(oldTopicId)) {
                        topicIds.set(i, topicPatch.getNewTopicId());
                    }
                }
            }
        }
    }
}
