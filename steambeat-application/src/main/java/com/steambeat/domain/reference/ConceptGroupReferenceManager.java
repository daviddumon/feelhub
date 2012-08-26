package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class ConceptGroupReferenceManager extends ReferenceManager {

    @Inject
    public ConceptGroupReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent) {
        sessionProvider.start();
        List<ConceptReferencesChangedEvent> conceptReferencesChangedEvents = Lists.newArrayList();
        for (ConceptTranslatedEvent conceptTranslatedEvent : conceptGroupTranslatedEvent.getConceptTranslatedEvents()) {
            final List<Reference> allReferences = getAllReferences(conceptTranslatedEvent);
            final Reference reference = getOldestReference(conceptTranslatedEvent, allReferences);
            setInactiveReferences(reference, allReferences);
            final ConceptReferencesChangedEvent conceptReferencesChangedEvent = createConceptReferencesChangedEvent(reference, allReferences);
            conceptReferencesChangedEvents.add(conceptReferencesChangedEvent);
        }
        postConceptGroupReferencesChangedEvent(conceptReferencesChangedEvents);
        sessionProvider.stop();
    }

    private void postConceptGroupReferencesChangedEvent(final List<ConceptReferencesChangedEvent> conceptGroupTranslatedEvent) {
        final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent = new ConceptGroupReferencesChangedEvent();
        conceptGroupReferencesChangedEvent.addAllAbsent(conceptGroupTranslatedEvent);
        DomainEventBus.INSTANCE.post(conceptGroupReferencesChangedEvent);
    }

    private SessionProvider sessionProvider;
}
