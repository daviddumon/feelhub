package com.feelhub.domain.feeling;

import com.feelhub.domain.related.RelatedBuilder;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.*;

public class FeelingRelationBinder {

    public FeelingRelationBinder(final RelatedBuilder relatedBuilder) {
        this.relatedBuilder = relatedBuilder;
    }

    public void bind(final Feeling feeling) {
        final List<Topic> topics = loadAllTopics(feeling.getRelatedTopics());
        createRelations(topics);
    }

    private List<Topic> loadAllTopics(final List<UUID> relatedIds) {
        final List<Topic> topics = Lists.newArrayList();
        for (final UUID id : relatedIds) {
            final Topic topic = Repositories.topics().getCurrentTopic(id);
            topics.add(topic);
        }
        return topics;
    }

    private void createRelations(final List<Topic> topics) {
        for (int i = 0; i < topics.size(); i++) {
            final Topic currentRealTopic = topics.get(i);
            connectTopics(currentRealTopic, topics, i + 1);
        }
    }

    private void connectTopics(final Topic from, final List<Topic> topics, final int beginningIndex) {
        for (int i = beginningIndex; i < topics.size(); i++) {
            final Topic to = topics.get(i);
            relatedBuilder.connectTwoWays(from, to);
        }
    }

    private final RelatedBuilder relatedBuilder;
}
