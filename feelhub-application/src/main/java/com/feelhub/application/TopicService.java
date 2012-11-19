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
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public Topic createTopic() {
        final Topic topic = topicFactory.createTopic();
        Repositories.topics().add(topic);
        return topic;
    }

    public void createTopicFromUri(final String uri) {

    }

    private final TopicFactory topicFactory;
}
