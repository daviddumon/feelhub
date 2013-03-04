package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class RealTopicCreatedEvent extends DomainEvent {

    public RealTopicCreatedEvent(final UUID topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Event id", topicId).toString();
    }

    public final UUID topicId;
}
