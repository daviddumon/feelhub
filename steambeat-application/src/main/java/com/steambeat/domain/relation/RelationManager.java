package com.steambeat.domain.relation;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.*;

import java.util.*;

public class RelationManager {

    @Inject
    public RelationManager(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final ReferencesChangedEvent event) {
        sessionProvider.start();
        for (final UUID referenceId : event.getReferenceIds()) {
            final List<Relation> relations = Repositories.relations().forReferenceId(referenceId);
            if (!relations.isEmpty()) {
                migrateRelations(event, referenceId, relations);
            }
        }
        sessionProvider.stop();
    }

    private void migrateRelations(final ReferencesChangedEvent event, final UUID referenceId, final List<Relation> relations) {
        for (final Relation relation : relations) {
            checkFromId(event.getNewReferenceId(), referenceId, relation);
            checkToId(event.getNewReferenceId(), referenceId, relation);
        }
    }

    private void checkFromId(final UUID newReference, final UUID referenceToChange, final Relation relation) {
        if (relation.getFromId().equals(referenceToChange)) {
            if (relation.getToId().equals(newReference)) {
                Repositories.relations().delete(relation);
            } else {
                final Relation relationFound = Repositories.relations().lookUp(newReference, relation.getToId());
                if (relationFound != null) {
                    relationFound.addWeight(relation.getWeight());
                    Repositories.relations().delete(relation);
                } else {
                    relation.setFromId(newReference);
                }
            }
        }
    }

    private void checkToId(final UUID newReference, final UUID referenceToChange, final Relation relation) {
        if (relation.getToId().equals(referenceToChange)) {
            if (relation.getFromId().equals(newReference)) {
                Repositories.relations().delete(relation);
            } else {
                final Relation relationFound = Repositories.relations().lookUp(relation.getFromId(), newReference);
                if (relationFound != null) {
                    relationFound.addWeight(relation.getWeight());
                    Repositories.relations().delete(relation);
                } else {
                    relation.setToId(newReference);
                }
            }
        }
    }

    private final SessionProvider sessionProvider;
}
