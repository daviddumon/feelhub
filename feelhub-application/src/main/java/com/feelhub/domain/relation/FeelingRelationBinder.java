package com.feelhub.domain.relation;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.List;

public class FeelingRelationBinder {

    @Inject
    public FeelingRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final Feeling feeling) {
        final List<Topic> topics = loadAllTopics(feeling.getSentiments());
        createRelations(topics);
    }

    private List<Topic> loadAllTopics(final List<Sentiment> sentiments) {
        final List<Topic> topics = Lists.newArrayList();
        for (final Sentiment sentiment : sentiments) {
            final Topic topic = Repositories.topics().get(sentiment.getTopicId());
            topics.add(topic);
        }
        return topics;
    }

    private void createRelations(final List<Topic> topics) {
        for (int i = 0; i < topics.size(); i++) {
            final Topic currentTopic = topics.get(i);
            connectTopics(currentTopic, topics, i + 1);
        }
    }

    private void connectTopics(final Topic from, final List<Topic> topics, final int beginningIndex) {
        for (int i = beginningIndex; i < topics.size(); i++) {
            final Topic to = topics.get(i);
            relationBuilder.connectTwoWays(from, to);
        }
    }

    private final RelationBuilder relationBuilder;
}