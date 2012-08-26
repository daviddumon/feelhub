package com.steambeat.domain.reference;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.uri.CompleteUriEvent;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class UriReferenceManager extends ReferenceManager {

    @Inject
    public UriReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final CompleteUriEvent event) {
        sessionProvider.start();
        final List<Reference> allReferences = getAllReferences(event);
        final Reference reference = getOldestReference(event, allReferences);
        setInactiveReferences(reference, allReferences);
        postUriReferencesChangedEvent(reference, allReferences);
        sessionProvider.stop();
    }

    private void postUriReferencesChangedEvent(final Reference newReference, final List<Reference> references) {
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
