package com.feelhub.domain.user.activation;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

import java.util.UUID;

public class UserActivatedEvent extends DomainEvent{


    public UserActivatedEvent(final UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("date", date).add("User Id", userId).toString();
    }

    public final UUID userId;
}
