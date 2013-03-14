package com.feelhub.domain.media;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class MediaCreatedEvent extends DomainEvent {

    public MediaCreatedEvent(final UUID fromId, final UUID toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("fromId", fromId).add("toId", toId).toString();
    }

    public UUID getFromId() {
        return fromId;
    }

    public UUID getToId() {
        return toId;
    }

    private final UUID fromId;
    private final UUID toId;
}
