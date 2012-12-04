package com.feelhub.domain.tag;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TagTestFactory {

    public Tag newTag() {
        return newTag("value");
    }

    public Tag newTag(final String value) {
        return newTag(value, createAndPersistTopic());
    }

    public Tag newTag(final String value, final Topic topic) {
        final Tag tag = new Tag(value);
        tag.addTopic(topic);
        Repositories.tags().add(tag);
        return tag;
    }

    private Topic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        Repositories.topics().add(topic);
        return topic;
    }
}
