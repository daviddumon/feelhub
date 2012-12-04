package com.feelhub.application;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

import java.util.UUID;

public class TopicService {

    @Inject
    public TopicService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public Topic lookUp(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic createTopic(final FeelhubLanguage feelhubLanguage, final String description, final TopicType topicType, final User user) {
        final Topic topic = topicFactory.createTopic(feelhubLanguage, description, topicType);
        topic.setUserId(user.getId());
        Repositories.topics().add(topic);
        return topic;
    }

    private final TopicFactory topicFactory;
}
