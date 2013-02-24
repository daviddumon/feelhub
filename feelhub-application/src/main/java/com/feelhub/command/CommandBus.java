package com.feelhub.command;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;

import javax.inject.Inject;

public class CommandBus {

    @Inject
    public CommandBus(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public <T> T execute(Commande<T> commande) {
        sessionProvider.start();
        try {
            return commande.execute();
        } finally {
            sessionProvider.stop();
            DomainEventBus.INSTANCE.propagate();
        }
    }

    private SessionProvider sessionProvider;
}
