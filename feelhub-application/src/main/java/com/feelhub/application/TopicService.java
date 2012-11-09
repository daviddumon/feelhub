package com.feelhub.application;

import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

import java.util.UUID;

public class TopicService {

    @Inject
    public TopicService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public Topic lookUp(final UUID id) {
        return Repositories.topics().getActive(id);
    }

    public Topic newTopic() {
        final Topic topic = topicFactory.createTopic();
        Repositories.topics().add(topic);
        return topic;
    }

    private final TopicFactory topicFactory;
}
