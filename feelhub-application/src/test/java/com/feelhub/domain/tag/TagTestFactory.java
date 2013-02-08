package com.feelhub.domain.tag;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.*;
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
        tag.addTopic(realTopic, FeelhubLanguage.reference());
        Repositories.tags().add(tag);
        return tag;
    }

    public Tag newTag(final String value, final HttpTopic topic) {
        final Tag tag = new Tag(value);
        tag.addTopic(topic, FeelhubLanguage.reference());
        Repositories.tags().add(tag);
        return tag;
    }

    public Tag newTag(final String value, final Topic topic, final FeelhubLanguage language) {
        final Tag tag = new Tag(value);
        tag.addTopic(topic, language);
        Repositories.tags().add(tag);
        return tag;
    }

    public Tag newTagWithoutTopic() {
        return newTagWithoutTopic("value");
    }

    public Tag newTagWithoutTopic(final String value) {
        final Tag tag = new Tag(value);
        Repositories.tags().add(tag);
        return tag;
    }

    private RealTopic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, RealTopicType.Automobile);
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public Tag newTag(final String value, final FeelhubLanguage language) {
        final Tag tag = new Tag(value);
        tag.addTopic(createAndPersistTopic(), language);
        Repositories.tags().add(tag);
        return tag;
    }
}
