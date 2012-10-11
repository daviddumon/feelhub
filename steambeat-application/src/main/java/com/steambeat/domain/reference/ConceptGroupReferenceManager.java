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
        final List<ConceptReferencesChangedEvent> conceptReferencesChangedEvents = Lists.newArrayList();
        for (final ConceptTranslatedEvent conceptTranslatedEvent : conceptGroupTranslatedEvent.getConceptTranslatedEvents()) {
            final List<Reference> allReferences = getAllReferences(conceptTranslatedEvent);
            final Reference reference = getOldestReference(allReferences);
            setInactiveReferences(reference, allReferences);
            final ConceptReferencesChangedEvent conceptReferencesChangedEvent = createConceptReferencesChangedEvent(reference, allReferences);
            conceptReferencesChangedEvents.add(conceptReferencesChangedEvent);
        }
        postConceptGroupReferencesChangedEvent(conceptReferencesChangedEvents, conceptGroupTranslatedEvent);
        sessionProvider.stop();
    }

    private void postConceptGroupReferencesChangedEvent(final List<ConceptReferencesChangedEvent> conceptReferencesChangedEvents, final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent) {
        final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent = new ConceptGroupReferencesChangedEvent(conceptGroupTranslatedEvent.getReferenceId());
        conceptGroupReferencesChangedEvent.addAllAbsent(conceptReferencesChangedEvents);
        DomainEventBus.INSTANCE.post(conceptGroupReferencesChangedEvent);
    }

    private final SessionProvider sessionProvider;
}
