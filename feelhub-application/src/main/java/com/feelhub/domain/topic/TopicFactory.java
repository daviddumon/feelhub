package com.feelhub.domain.topic;

import java.util.UUID;

public class TopicFactory {

    public Topic createTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        return topic;
    }

    public Topic createWorld() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        topic.setType(TopicType.World);
        return topic;
    }
}
