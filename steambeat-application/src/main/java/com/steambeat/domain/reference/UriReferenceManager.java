package com.steambeat.domain.reference;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.uri.CompleteUriEvent;
import com.steambeat.repositories.SessionProvider;

public class UriReferenceManager extends ReferenceManager {

    @Inject
    public UriReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final CompleteUriEvent event) {
        sessionProvider.start();
        getAllReferences(event);
        final Reference reference = getOldestReference(event);
        setInactiveReferences(reference);
        postUriReferencesChangedEvent(reference);
        sessionProvider.stop();
    }

    private void postUriReferencesChangedEvent(final Reference newReference) {
        final UriReferencesChangedEvent event = new UriReferencesChangedEvent(newReference.getId());
        for (Reference reference : references) {
            if (!reference.isActive()) {
                event.addReferenceToChange(reference.getId());
            }
        }
        DomainEventBus.INSTANCE.post(event);
    }


    private SessionProvider sessionProvider;
}
