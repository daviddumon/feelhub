package com.feelhub.domain.alchemy;

import com.feelhub.domain.related.RelatedBuilder;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class AlchemyRelationBinder {

    @Inject
    public AlchemyRelationBinder(final RelatedBuilder relatedBuilder) {
        this.relatedBuilder = relatedBuilder;
    }

    public void bind(final UUID mainTopicId, final Map<UUID, Double> topicIds) {
        final Topic mainRealTopic = loadTopic(mainTopicId);
        final List<Topic> realTopics = Lists.newArrayList();
        connectAllTopicsToMainTopicWithScore(topicIds, mainRealTopic, realTopics);
        connectAllTopicsToThemselves(realTopics);
    }

    private void connectAllTopicsToMainTopicWithScore(final Map<UUID, Double> topicIds, final Topic to, final List<Topic> realTopics) {
        for (final Map.Entry<UUID, Double> entry : topicIds.entrySet()) {
            final UUID topicId = entry.getKey();
            final Double score = entry.getValue();
            final Topic from = loadTopic(topicId);
            relatedBuilder.connectTwoWays(from, to, score);
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
            relatedBuilder.connectTwoWays(from, to);
        }
    }

    private Topic loadTopic(final UUID topicId) {
        return Repositories.topics().getCurrentTopic(topicId);
    }

    private final RelatedBuilder relatedBuilder;
}