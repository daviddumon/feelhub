package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class FeelingCreatedEvent extends DomainEvent {

    public FeelingCreatedEvent(final UUID feelingId, final UUID userId) {
        this.feelingId = feelingId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Date", date).add("Feeling id", feelingId).toString();
    }

    public final UUID feelingId;
    public final UUID userId;
}
