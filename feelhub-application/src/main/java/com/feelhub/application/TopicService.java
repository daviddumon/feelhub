package com.feelhub.application;

import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class TopicService {

    @Inject
    public TopicService(final TopicFactory topicFactory, final Scraper scraper, final TagService tagService) {
        this.topicFactory = topicFactory;
        this.scraper = scraper;
        this.tagService = tagService;
    }

    public Topic lookUp(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic lookUpCurrent(final UUID id) {
        final Topic topic = Repositories.topics().getCurrentTopic(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic lookUp(final String value, final RealTopicType type) {
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicIds()) {
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

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, final User user) {
        final RealTopic realTopic = createRealTopic(feelhubLanguage, name, type);
        realTopic.setUserId(user.getId());
        return realTopic;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final RealTopic realTopic = topicFactory.createRealTopic(feelhubLanguage, name, type);
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public HttpTopic createHttpTopic() {
        // APPELER URI RESOLVER ICI PUIS LA FACTORY
        return null;
    }

    public List<Topic> getTopics(final String value) {
        final List<Topic> topics = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(value);
            for (final UUID id : tag.getTopicIds()) {
                try {
                    topics.add(lookUpCurrent(id));
                } catch (TopicNotFound e) {
                }
            }
        } catch (TagNotFoundException e) {
        }
        return topics;
    }

    private final Scraper scraper;
    private final TopicFactory topicFactory;
    private final TagService tagService;
}
