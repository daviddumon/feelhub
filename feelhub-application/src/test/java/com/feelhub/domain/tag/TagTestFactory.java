package com.feelhub.domain.tag;

import com.feelhub.domain.topic.usable.real.*;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TagTestFactory {

    public Tag newTag() {
        return newTag("value");
    }

    public Tag newTag(final String value) {
        return newTag(value, createAndPersistTopic());
    }

    public Tag newTag(final String value, final RealTopic realTopic) {
        final Tag tag = new Tag(value);
        tag.addTopic(realTopic);
        Repositories.tags().add(tag);
        return tag;
    }

    public Tag newTagWithoutTopic() {
        final Tag tag = new Tag("value");
        Repositories.tags().add(tag);
        return tag;
    }

    private RealTopic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, RealTopicType.Automobile);
        Repositories.topics().add(realTopic);
        return realTopic;
    }
}
