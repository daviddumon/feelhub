package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

public class TagRequestEvent extends DomainEvent {

    public TagRequestEvent(final Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Tag request event";
    }

    public Topic getTopic() {
        return topic;
    }

    private Topic topic;
}
