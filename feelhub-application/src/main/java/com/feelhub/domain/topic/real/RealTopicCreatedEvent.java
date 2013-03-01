package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class RealTopicCreatedEvent extends DomainEvent {
    public RealTopicCreatedEvent(final UUID eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Event id", eventId).toString();
    }

    public final UUID eventId;
}
