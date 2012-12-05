package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.FeelingManager;
import com.feelhub.domain.meta.IllustrationManager;
import com.feelhub.domain.relation.RelationManager;
import com.feelhub.domain.statistics.StatisticsManager;
import com.feelhub.domain.tag.TagManager;

import java.util.UUID;

public class TopicMerger {

    public void merge(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = createTopicPatch(newTopicId, oldTopicId);
        tagManager.merge(topicPatch);
        illustrationManager.merge(topicPatch);
        feelingManager.merge(topicPatch);
        relationManager.merge(topicPatch);
        statisticsManager.merge(topicPatch);
    }

    private TopicPatch createTopicPatch(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = new TopicPatch(newTopicId);
        topicPatch.addOldTopicId(oldTopicId);
        return topicPatch;
    }

    private final TagManager tagManager = new TagManager();
    private final IllustrationManager illustrationManager = new IllustrationManager();
    private final FeelingManager feelingManager = new FeelingManager();
    private final RelationManager relationManager = new RelationManager();
    private final StatisticsManager statisticsManager = new StatisticsManager();
}
