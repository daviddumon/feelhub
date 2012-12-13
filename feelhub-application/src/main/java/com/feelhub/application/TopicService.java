package com.feelhub.application;

import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.web.WebTopic;
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

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType realTopicType, final User user) {
        final RealTopic realTopic = topicFactory.createRealTopic(feelhubLanguage, name, realTopicType);
        realTopic.setUserId(user.getId());
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public WebTopic createWebTopic() {
        return null;
    }

    public List<Topic> getTopics(final String value) {
        final Tag tag = tagService.lookUp(value);
        final List<Topic> topics = Lists.newArrayList();
        for (final UUID id : tag.getTopicIds()) {
            try {
                topics.add(lookUp(id));
            } catch (TopicNotFound e) {
            }
        }
        return topics;
    }

    private final TopicFactory topicFactory;
    private final Scraper scraper;
    private final TagService tagService;
}
