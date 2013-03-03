package com.feelhub.domain.user.activation;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class ActivationCreatedEvent extends DomainEvent {

    public ActivationCreatedEvent(final UUID userId, final UUID activationId) {
        this.userId = userId;
        this.activationId = activationId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("User Id", userId).add("Activation id", activationId).toString();
    }

    public final UUID userId;
    public final UUID activationId;
}
