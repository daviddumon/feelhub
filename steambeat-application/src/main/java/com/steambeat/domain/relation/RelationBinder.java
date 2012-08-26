package com.steambeat.domain.relation;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.alchemy.Alchemy;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.*;

import java.util.*;

public class RelationBinder {

    @Inject
    public RelationBinder(final SessionProvider sessionProvider, final RelationBuilder relationBuilder) {
        this.sessionProvider = sessionProvider;
        this.relationBuilder = relationBuilder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent) {
        sessionProvider.start();
        List<Reference> references = getReferencesToBind(conceptGroupReferencesChangedEvent);
        final double relevanceScore = getRelevanceScore(conceptGroupReferencesChangedEvent.getReferenceId());
        bindReferences(references, relevanceScore);
        postEvents(conceptGroupReferencesChangedEvent);
        sessionProvider.stop();
    }

    private List<Reference> getReferencesToBind(final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent) {
        List<Reference> references = Lists.newArrayList();
        for (ConceptReferencesChangedEvent conceptReferencesChangedEvent : conceptGroupReferencesChangedEvent.getConceptReferencesChangedEvents()) {
            references.add(Repositories.references().get(conceptReferencesChangedEvent.getNewReferenceId()));
        }
        references.add(Repositories.references().get(conceptGroupReferencesChangedEvent.getReferenceId()));
        return references;
    }

    private double getRelevanceScore(final UUID referenceId) {
        final List<Alchemy> alchemy = Repositories.alchemys().forReferenceId(referenceId);
        if (!alchemy.isEmpty()) {
            return alchemy.get(0).getRelevance();
        }
        return 0;
    }

    private void bindReferences(final List<Reference> references, final double relevanceScore) {
        for (int i = 0; i < references.size(); i++) {
            Reference currentReference = references.get(i);
            connectReference(currentReference, references, i + 1, relevanceScore);
        }
    }

    private void connectReference(final Reference currentReference, final List<Reference> references, final int beginningIndex, final double relevanceScore) {
        for (int i = beginningIndex; i < references.size(); i++) {
            relationBuilder.connectTwoWays(currentReference, references.get(i), relevanceScore);
        }
    }

    private void postEvents(final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent) {
        for (ConceptReferencesChangedEvent conceptReferencesChangedEvent : conceptGroupReferencesChangedEvent.getConceptReferencesChangedEvents()) {
            DomainEventBus.INSTANCE.post(conceptReferencesChangedEvent);
        }
    }

    private SessionProvider sessionProvider;
    private RelationBuilder relationBuilder;
}