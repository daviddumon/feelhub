package com.feelhub.domain.user.activation;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class Activation extends BaseEntity {

    protected Activation() {
        // for mongolink
    }

    public Activation(final User user) {
        this.id = UUID.randomUUID();
        this.userId = user.getId();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void confirm() {
        Repositories.users().get(userId).activate();
        DomainEventBus.INSTANCE.post(new UserActivatedEvent(userId));
    }

    private UUID id;

    private UUID userId;
}
