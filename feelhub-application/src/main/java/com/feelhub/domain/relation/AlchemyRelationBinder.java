package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class AlchemyRelationBinder {

    @Inject
    public AlchemyRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final UUID mainTopicId, final HashMap<UUID, Double> topicIds) {
        final Topic mainTopic = loadtopic(mainTopicId);
        final List<Topic> topics = Lists.newArrayList();
        connectAllTopicsToMainTopicWithScore(topicIds, mainTopic, topics);
        connectAllTopicsToThemselves(topics);
    }

    private void connectAllTopicsToThemselves(final List<Topic> topics) {
        for (int i = 0; i < topics.size(); i++) {
            final Topic currentTopic = topics.get(i);
            connectTopic(currentTopic, i + 1, topics);
        }
    }

    private void connectTopic(final Topic from, final int beginningIndex, final List<Topic> topics) {
        for (int i = beginningIndex; i < topics.size(); i++) {
            final Topic to = topics.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private void connectAllTopicsToMainTopicWithScore(final HashMap<UUID, Double> topicIds, final Topic to, final List<Topic> topics) {
        for (final Map.Entry<UUID, Double> entry : topicIds.entrySet()) {
            final UUID currentTopicId = entry.getKey();
            final Double score = entry.getValue();
            final Topic from = loadtopic(currentTopicId);
            relationBuilder.connectTwoWays(from, to, score);
            topics.add(from);
        }
    }

    private Topic loadtopic(final UUID currentTopicId) {
        return Repositories.topics().get(currentTopicId);
    }

    private final RelationBuilder relationBuilder;
}