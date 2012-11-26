package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class TopicFactory {

    public Topic createTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        return topic;
    }

    public Topic createTopic(final FeelhubLanguage feelhubLanguage,final String description, final TopicType topicType) {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        topic.addDescription(feelhubLanguage, description);
        topic.setType(topicType);
        return topic;
    }

    public Topic createWorld() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        topic.setType(TopicType.World);
        return topic;
    }
}
