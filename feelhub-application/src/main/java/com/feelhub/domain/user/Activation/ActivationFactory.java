package com.feelhub.domain.user.activation;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.User;

public class ActivationFactory {
    public Activation createForUser(User user) {
        final Activation activation = new Activation(user);
        DomainEventBus.INSTANCE.post(new ActivationCreatedEvent(user.getId(), activation.getId()));
        return activation;
    }
}
