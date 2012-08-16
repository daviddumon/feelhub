package com.steambeat.domain.topic;

import java.util.UUID;

public class TopicFactory {

    public Topic createTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        return topic;
    }
}
