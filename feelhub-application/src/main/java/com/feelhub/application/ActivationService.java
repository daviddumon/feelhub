package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.UUID;

public class ActivationService {

    @Inject
    public ActivationService() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onUserCreated(final UserCreatedEvent event) {
        createActivation(event.getUser());
    }

    void createActivation(User user) {
        if (user.isActive()) {
            return;
        }
        final Activation activation = new Activation(user);
        Repositories.activation().add(activation);
        DomainEventBus.INSTANCE.post(new ActivationCreatedEvent(user, activation));
    }

    public void confirm(final UUID id) {
        final Activation activation = Repositories.activation().get(id);
        if (activation == null) {
            throw new ActivationNotFoundException();
        }
        activation.confirm();
        Repositories.activation().delete(activation);
    }

}
