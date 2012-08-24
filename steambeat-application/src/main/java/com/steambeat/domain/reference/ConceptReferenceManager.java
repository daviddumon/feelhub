package com.steambeat.domain.reference;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.concept.ConceptTranslatedEvent;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.repositories.SessionProvider;

public class ConceptReferenceManager extends ReferenceManager {

    @Inject
    public ConceptReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ConceptTranslatedEvent event) {
        sessionProvider.start();
        getAllReferences(event);
        final Reference reference = getOldestReference(event);
        setInactiveReferences(reference);
        postConceptReferencesChangedEvent(reference);
        sessionProvider.stop();
    }

    private void postConceptReferencesChangedEvent(final Reference newReference) {
        final ConceptReferencesChangedEvent eventConcept = new ConceptReferencesChangedEvent(newReference.getId());
        for (Reference reference : references) {
            if (!reference.isActive()) {
                eventConcept.addReferenceToChange(reference.getId());
            }
        }
        DomainEventBus.INSTANCE.post(eventConcept);
    }

    private SessionProvider sessionProvider;
}
