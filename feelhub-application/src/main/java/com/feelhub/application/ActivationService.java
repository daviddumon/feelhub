package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.user.*;
import com.feelhub.repositories.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.UUID;

public class ActivationService {

    @Inject
    public ActivationService(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onUserCreated(final UserCreatedEvent event) {
        sessionProvider.start();
        if (event.getUser().isActive()) {
            return;
        }
        final Activation activation = new Activation(event.getUser());
        Repositories.activation().add(activation);
        DomainEventBus.INSTANCE.post(new ActivationCreatedEvent(event.getUser(), activation));
        sessionProvider.stop();
    }

    public void confirm(final UUID id) {
        final Activation activation = Repositories.activation().get(id);
        if (activation == null) {
            throw new ActivationNotFoundException();
        }
        activation.confirm();
        Repositories.activation().delete(activation);
    }

    private SessionProvider sessionProvider;
}
