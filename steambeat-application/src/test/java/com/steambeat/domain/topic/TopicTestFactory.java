package com.steambeat.domain.topic;

import com.steambeat.domain.association.Association;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class TopicTestFactory {

    public Topic newWebPageFor(final Association association) {
        return null;
    }

    public Topic newTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        Repositories.topics().add(topic);
        return topic;
    }
}
