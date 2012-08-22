package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.*;

import java.util.*;

public class ReferenceManager {

    @Inject
    public ReferenceManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ConceptTranslatedEvent event) {
        sessionProvider.start();
        getAllReferences(event.getConcept());
        final Reference reference = getOldestReference(event.getConcept());
        setInactiveReferences(reference);
        postEvent(reference);
        sessionProvider.stop();
    }

    private void getAllReferences(final Concept concept) {
        references = Lists.newArrayList();
        for (Keyword keyword : concept.getKeywords()) {
            final UUID referenceId = keyword.getReferenceId();
            final Reference reference = Repositories.references().get(referenceId);
            references.add(reference);
        }
    }

    private Reference getOldestReference(final Concept concept) {
        Reference result = references.get(0);
        for (int i = 1; i < references.size(); i++) {
            final Reference current = references.get(i);
            if (current.getCreationDate().isBefore(result.getCreationDate())) {
                result = current;
            }
        }
        return result;
    }

    private void setInactiveReferences(final Reference reference) {
        for (Reference current : references) {
            if (!current.equals(reference)) {
                current.setActive(false);
            }
        }
    }

    private void postEvent(final Reference newReference) {
        final ReferencesChangedEvent event = new ReferencesChangedEvent(newReference.getId());
        for (Reference reference : references) {
            if (!reference.isActive()) {
                event.addReferenceToChange(reference.getId());
            }
        }
        DomainEventBus.INSTANCE.post(event);
    }

    private List<Reference> references;
    private SessionProvider sessionProvider;
}
