package com.feelhub.domain.tag;

import com.feelhub.application.TagService;
import com.feelhub.application.TopicService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.topic.Topic;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.UUID;

public class TagIndexer {

    @Inject
    public TagIndexer(final TagService tagService, final TopicService topicService) {
        this.tagService = tagService;
        this.topicService = topicService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onTagRequest(final TagRequestEvent tagRequestEvent) {
        final String name = tagRequestEvent.getName();
        final Topic topic = tagRequestEvent.getTopic();
        final Tag tag = lookUpOrCreateTag(name);
        index(topic, tag);
    }

    private void index(final Topic topic, final Tag tag) {
        if (topic.getType().hasTagUniqueness()) {
            addIfNotPresent(topic, tag);
        } else {
            addTopicToTag(topic, tag);
        }
    }

    private void addIfNotPresent(final Topic topic, final Tag tag) {
        for (final UUID id : tag.getTopicIds()) {
            final Topic existingTopic = topicService.lookUpCurrent(id);
            if (existingTopic.getType().equals(topic.getType())) {
                topic.changeCurrentId(existingTopic.getId());
                return;
            }
        }
        tag.addTopic(topic);
    }

    private void addTopicToTag(final Topic topic, final Tag tag) {
        if (!tag.getTopicIds().contains(topic.getId())) {
            tag.addTopic(topic);
        }
    }

    private Tag lookUpOrCreateTag(final String description) {
        try {
            return tagService.lookUp(description);
        } catch (TagNotFoundException e) {
            return tagService.createTag(description);
        }
    }

    private TagService tagService;
    private TopicService topicService;
}
