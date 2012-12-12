package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

public class TagRequestEvent extends DomainEvent {

    public TagRequestEvent(final Topic topic, final String name) {
        this.topic = topic;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag request event";
    }

    public Topic getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    private final Topic topic;
    private final String name;
}
