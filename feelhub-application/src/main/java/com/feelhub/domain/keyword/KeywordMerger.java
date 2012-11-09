package com.feelhub.domain.keyword;

import com.feelhub.domain.feeling.FeelingManager;
import com.feelhub.domain.illustration.IllustrationManager;
import com.feelhub.domain.relation.RelationManager;
import com.feelhub.domain.statistics.StatisticsManager;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.*;

public class KeywordMerger {

    public void merge(final List<Keyword> keywords) {
        final TopicPatch topicPatch = createTopicPatch(keywords);
        keywordManager.merge(topicPatch);
        topicManager.merge(topicPatch);
        illustrationManager.merge(topicPatch);
        feelingManager.merge(topicPatch);
        relationManager.merge(topicPatch);
        statisticsManager.merge(topicPatch);
    }

    private TopicPatch createTopicPatch(final List<Keyword> keywords) {
        final List<Topic> allTopics = getAllTopics(keywords);
        final Topic oldestTopic = getOldestTopic(allTopics);
        final TopicPatch topicPatch = new TopicPatch(oldestTopic.getId());
        appendOldTopics(topicPatch, allTopics);
        return topicPatch;
    }

    private List<Topic> getAllTopics(final List<Keyword> keywords) {
        final List<Topic> topics = Lists.newArrayList();
        for (final Keyword keyword : keywords) {
            final UUID topicId = keyword.getTopicId();
            final Topic topic = Repositories.topics().get(topicId);
            topics.add(topic);
        }
        return topics;
    }

    private Topic getOldestTopic(final List<Topic> topics) {
        Topic result = topics.get(0);
        for (int i = 1; i < topics.size(); i++) {
            final Topic current = topics.get(i);
            if (current.getCreationDate().isBefore(result.getCreationDate())) {
                result = current;
            }
        }
        return result;
    }

    private void appendOldTopics(final TopicPatch topicPatch, final List<Topic> allTopics) {
        for (final Topic topic : allTopics) {
            final UUID currentId = topic.getId();
            if (!currentId.equals(topicPatch.getNewTopicId())) {
                topicPatch.addOldTopicId(currentId);
            }
        }
    }

    private final KeywordManager keywordManager = new KeywordManager();
    private final TopicManager topicManager = new TopicManager();
    private final IllustrationManager illustrationManager = new IllustrationManager();
    private final FeelingManager feelingManager = new FeelingManager();
    private final RelationManager relationManager = new RelationManager();
    private final StatisticsManager statisticsManager = new StatisticsManager();
}
