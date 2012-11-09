package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class FeelingManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Feeling> feelingsForOldTopicId = Repositories.feelings().forTopicId(oldTopicId);
            if (!feelingsForOldTopicId.isEmpty()) {
                for (final Feeling feeling : feelingsForOldTopicId) {
                    for (final Sentiment sentiment : feeling.getSentiments()) {
                        if (sentiment.getTopicId().equals(oldTopicId)) {
                            sentiment.setTopicId(topicPatch.getNewTopicId());
                        }
                    }
                }
            }
        }
    }
}
