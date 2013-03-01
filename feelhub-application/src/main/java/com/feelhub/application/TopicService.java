package com.feelhub.application;

import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class TopicService {

    @Inject
    public TopicService(final TagService tagService) {
        this.tagService = tagService;
    }

    public Topic lookUp(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic lookUpTopic(final String value, final RealTopicType type, final FeelhubLanguage language) {
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicsIdFor(language)) {
                try {
                    final Topic topic = lookUpCurrent(id);
                    if (topic.getType().equals(type)) {
                        return topic;
                    }
                } catch (TopicNotFound e) {
                }
            }
        } catch (TagNotFoundException e) {
        }
        return null;
    }

    public List<Topic> getTopics(final String value, final FeelhubLanguage language) {
        final List<Topic> topics = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(value);
            addTopicsForLanguage(language, topics, tag);
            addTopicsForLanguage(FeelhubLanguage.none(), topics, tag);
        } catch (TagNotFoundException e) {
        }
        return topics;
    }

    private void addTopicsForLanguage(final FeelhubLanguage language, final List<Topic> topics, final Tag tag) {
        for (final UUID id : tag.getTopicsIdFor(language)) {
            try {
                topics.add(lookUpCurrent(id));
            } catch (TopicNotFound e) {
            }
        }
    }

    public Topic lookUpCurrent(final UUID id) {
        final Topic topic = Repositories.topics().getCurrentTopic(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }


    private final TagService tagService;
}
