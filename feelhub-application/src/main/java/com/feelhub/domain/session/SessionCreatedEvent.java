package com.feelhub.domain.session;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class SessionCreatedEvent extends DomainEvent {
    public SessionCreatedEvent(UUID sessionId, UUID userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Session id", sessionId).add("User id", userId).toString();
    }

    public final UUID sessionId;
    public final UUID userId;
}
