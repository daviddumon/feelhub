package com.feelhub.domain.translation;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

public class ReferenceTranslatioRequestEvent extends DomainEvent {

    public ReferenceTranslatioRequestEvent(final Topic topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Topic getTopic() {
        return topic;
    }

    private final Topic topic;
}
