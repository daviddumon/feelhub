package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.FeelingManager;
import com.feelhub.domain.relation.RelationManager;
import com.feelhub.domain.statistics.StatisticsManager;
import com.feelhub.domain.tag.TagManager;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class TopicMerger {

    public void merge(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = createTopicPatch(newTopicId, oldTopicId);
        tagManager.merge(topicPatch);
        feelingManager.merge(topicPatch);
        relationManager.merge(topicPatch);
        statisticsManager.merge(topicPatch);
        mergeData(newTopicId, oldTopicId);
    }

    private TopicPatch createTopicPatch(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = new TopicPatch(newTopicId);
        topicPatch.addOldTopicId(oldTopicId);
        return topicPatch;
    }

    private void mergeData(final UUID newTopicId, final UUID oldTopicId) {
        //Do not use currentTopicId here! We cant to merge existing one!
        final Topic newTopic = Repositories.topics().get(newTopicId);
        final Topic oldTopic = Repositories.topics().get(oldTopicId);
        mergeNames(newTopic, oldTopic);
        mergeDescriptions(newTopic, oldTopic);
        mergeIllustrationLink(newTopic, oldTopic);
        mergeSubtypes(newTopic, oldTopic);
        mergeUrls(newTopic, oldTopic);
    }

    private void mergeNames(final Topic newTopic, final Topic oldTopic) {
        final Iterator<Map.Entry<String, String>> iterator = oldTopic.getNames().entrySet().iterator();
        final Map<String, String> descriptions = newTopic.getNames();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> next = iterator.next();
            if (!descriptions.containsKey(next.getKey())) {
                newTopic.addName(FeelhubLanguage.fromCode(next.getKey()), next.getValue());
            }
        }
    }

    private void mergeDescriptions(final Topic newTopic, final Topic oldTopic) {
        final Iterator<Map.Entry<String, String>> iterator = oldTopic.getDescriptions().entrySet().iterator();
        final Map<String, String> descriptions = newTopic.getDescriptions();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> next = iterator.next();
            if (!descriptions.containsKey(next.getKey())) {
                newTopic.addDescription(FeelhubLanguage.fromCode(next.getKey()), next.getValue());
            }
        }
    }

    private void mergeIllustrationLink(final Topic newTopic, final Topic oldTopic) {
        if (newTopic.getIllustrationLink().isEmpty()) {
            if (!oldTopic.getIllustrationLink().isEmpty()) {
                newTopic.setIllustrationLink(oldTopic.getIllustrationLink());
            }
        }
    }

    private void mergeSubtypes(final Topic newTopic, final Topic oldTopic) {
        for (final String subtype : oldTopic.getSubTypes()) {
            if (!newTopic.getSubTypes().contains(subtype)) {
                newTopic.addSubType(subtype);
            }
        }
    }

    private void mergeUrls(final Topic newTopic, final Topic oldTopic) {
        for (final String url : oldTopic.getUrls()) {
            if (!newTopic.getUrls().contains(url)) {
                newTopic.addUrl(url);
            }
        }
    }

    private final TagManager tagManager = new TagManager();
    private final FeelingManager feelingManager = new FeelingManager();
    private final RelationManager relationManager = new RelationManager();
    private final StatisticsManager statisticsManager = new StatisticsManager();
}
