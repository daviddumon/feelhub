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
        final Topic mainRealTopic = loadtopic(mainTopicId);
        final List<Topic> realTopics = Lists.newArrayList();
        connectAllTopicsToMainTopicWithScore(topicIds, mainRealTopic, realTopics);
        connectAllTopicsToThemselves(realTopics);
    }

    private void connectAllTopicsToMainTopicWithScore(final HashMap<UUID, Double> topicIds, final Topic to, final List<Topic> realTopics) {
        for (final Map.Entry<UUID, Double> entry : topicIds.entrySet()) {
            final UUID topicId = entry.getKey();
            final Double score = entry.getValue();
            final Topic from = loadtopic(topicId);
            relationBuilder.connectTwoWays(from, to, score);
            realTopics.add(from);
        }
    }

    private void connectAllTopicsToThemselves(final List<Topic> realTopics) {
        for (int i = 0; i < realTopics.size(); i++) {
            final Topic currentRealTopic = realTopics.get(i);
            connectTopic(currentRealTopic, i + 1, realTopics);
        }
    }

    private void connectTopic(final Topic from, final int beginningIndex, final List<Topic> realTopics) {
        for (int i = beginningIndex; i < realTopics.size(); i++) {
            final Topic to = realTopics.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private Topic loadtopic(final UUID topicId) {
        return Repositories.topics().getCurrentTopic(topicId);
    }

    private final RelationBuilder relationBuilder;
}