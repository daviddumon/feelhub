package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.Activation;
import com.feelhub.domain.user.UserConfirmationMailEvent;
import com.feelhub.domain.user.UserCreatedEvent;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;

import java.util.UUID;

public class ActivationService {

    public ActivationService() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onUserCreated(UserCreatedEvent event) {
        if(event.getUser().isActive()) {
            return;
        }
        final Activation activation = new Activation(event.getUser());
        Repositories.activation().add(activation);
        DomainEventBus.INSTANCE.post(new UserConfirmationMailEvent(event.getUser(), activation));
    }

    public void confirm(UUID id) {
        final Activation activation = Repositories.activation().get(id);
        if(activation == null) {
            throw new ActivationNotFoundException();
        }
        activation.confirm();
        Repositories.activation().delete(activation);
    }
}
