package com.steambeat.domain.reference;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.concept.ConceptTranslatedEvent;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class ConceptReferenceManager extends ReferenceManager {

    @Inject
    public ConceptReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptTranslatedEvent event) {
        sessionProvider.start();
        final List<Reference> allReferences = getAllReferences(event);
        final Reference reference = getOldestReference(event, allReferences);
        setInactiveReferences(reference, allReferences);
        postConceptReferencesChangedEvent(reference, allReferences);
        sessionProvider.stop();
    }

    private void postConceptReferencesChangedEvent(final Reference newReference, final List<Reference> references) {
        final ConceptReferencesChangedEvent conceptReferencesChangedEvent = createConceptReferencesChangedEvent(newReference, references);
        DomainEventBus.INSTANCE.post(conceptReferencesChangedEvent);
    }

    private final SessionProvider sessionProvider;
}
