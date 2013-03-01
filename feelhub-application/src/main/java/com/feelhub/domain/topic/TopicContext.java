package com.feelhub.domain.topic;

import com.feelhub.application.TopicService;
import com.feelhub.domain.related.Related;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import java.util.*;

public class TopicContext {

    @Inject
    public TopicContext(final TopicService topicService) {
        this.topicService = topicService;
    }

    public Map<Tag, Topic> extractFor(final UUID topicId, final FeelhubLanguage language) {
        final Map<Tag, Topic> results = Maps.newHashMap();
        final List<Related> relatedList = Repositories.related().forTopicId(topicId);
        for (final Related related : relatedList) {
            try {
                final RealTopic topic = (RealTopic) topicService.lookUpCurrent(related.getToId());
                final List<Tag> tags = Repositories.tags().forTopicId(related.getToId());
                for (final Tag tag : tags) {
                    if (isTagIndexingTopicInGoodLanguage(language, topic, tag)) {
                        results.put(tag, topic);
                    }
                }
            } catch (Exception e) {
            }
        }
        return results;
    }

    private boolean isTagIndexingTopicInGoodLanguage(final FeelhubLanguage language, final Topic topic, final Tag tag) {
        for (final UUID id : tag.getTopicsIdFor(language)) {
            if (id.equals(topic.getCurrentId())) {
                return true;
            }
        }
        for (final UUID id : tag.getTopicsIdFor(FeelhubLanguage.none())) {
            if (id.equals(topic.getCurrentId())) {
                return true;
            }
        }
        return false;
    }

    private final TopicService topicService;
}
