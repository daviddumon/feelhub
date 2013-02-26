package com.feelhub.domain.topic;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.tag.TagFactory;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TopicIndexer {
    public TopicIndexer(Topic topic) {
        this.topic = topic;
    }

    public void index(final String value, final FeelhubLanguage language) {
        final Tag tag = lookUpOrCreateTag(value);
        if (topic.getType().isTranslatable()) {
            indexForLanguage(topic, tag, language);
        } else {
            indexForLanguage(topic, tag, FeelhubLanguage.none());
        }
    }

    private Tag lookUpOrCreateTag(final String name) {
        Tag tag = Repositories.tags().get(name.toLowerCase());
        if (tag == null) {
            tag = new TagFactory().createTag(name.toLowerCase());
            Repositories.tags().add(tag);
        }
        return tag;
    }

    private void indexForLanguage(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        if (topic.getType().hasTagUniqueness()) {
            addTopicIfNotPresent(topic, tag, language);
        } else {
            addTopicToTag(topic, tag, language);
        }
    }

    private void addTopicToTag(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        if (!tag.getTopicsIdFor(language).contains(topic.getId())) {
            tag.addTopic(topic, language);
        }
    }

    private void addTopicIfNotPresent(final Topic topic, final Tag tag, final FeelhubLanguage language) {
        for (final UUID id : tag.getTopicsIdFor(language)) {
            final Topic existingTopic = Repositories.topics().getCurrentTopic(id);
            if (existingTopic.getType().equals(topic.getType())) {
                topic.changeCurrentId(existingTopic.getId());
                return;
            }
        }
        tag.addTopic(topic, language);
    }

    private Topic topic;
}
