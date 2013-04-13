package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.FeelingManager;
import com.feelhub.domain.related.RelatedManager;
import com.feelhub.domain.statistics.StatisticsManager;
import com.feelhub.domain.tag.TagManager;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class TopicMerger {

    public void merge(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = createTopicPatch(newTopicId, oldTopicId);
        tagManager.merge(topicPatch);
        feelingManager.merge(topicPatch);
        relatedManager.merge(topicPatch);
        statisticsManager.merge(topicPatch);
        mergeData(newTopicId, oldTopicId);
    }

    private TopicPatch createTopicPatch(final UUID newTopicId, final UUID oldTopicId) {
        final TopicPatch topicPatch = new TopicPatch(newTopicId);
        topicPatch.addOldTopicId(oldTopicId);
        return topicPatch;
    }

    private void mergeData(final UUID newTopicId, final UUID oldTopicId) {
        //Do not use currentTopicId here! We want to merge the existing one!
        final Topic newTopic = Repositories.topics().get(newTopicId);
        final Topic oldTopic = Repositories.topics().get(oldTopicId);
        mergeNames(newTopic, oldTopic);
        mergeDescriptions(newTopic, oldTopic);
        mergeIllustrationLink(newTopic, oldTopic);
        mergeSubtypes(newTopic, oldTopic);
        mergeUris(newTopic, oldTopic);
    }

    private void mergeNames(final Topic newTopic, final Topic oldTopic) {
        final Iterator<Map.Entry<String, String>> iterator = oldTopic.getNames().entrySet().iterator();
        final Map<String, String> names = newTopic.getNames();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> next = iterator.next();
            if (!names.containsKey(next.getKey())) {
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
        if (newTopic.getIllustration().isEmpty()) {
            if (!oldTopic.getIllustration().isEmpty()) {
                newTopic.setIllustration(oldTopic.getIllustration());
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

    private void mergeUris(final Topic newTopic, final Topic oldTopic) {
        for (final Uri uri : oldTopic.getUris()) {
            if (!newTopic.getUris().contains(uri)) {
                newTopic.addUri(uri);
            }
        }
    }

    private final TagManager tagManager = new TagManager();
    private final FeelingManager feelingManager = new FeelingManager();
    private final RelatedManager relatedManager = new RelatedManager();
    private final StatisticsManager statisticsManager = new StatisticsManager();
}
