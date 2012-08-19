package com.steambeat.domain.relation;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class RelationManager {

    public RelationManager() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final ReferencesChangedEvent event) {
        for (UUID referenceId : event.getReferenceIds()) {
            final List<Relation> relations = Repositories.relations().forReferenceId(referenceId);
            if (!relations.isEmpty()) {
                migrateRelations(event, referenceId, relations);
            }
        }
    }

    private void migrateRelations(final ReferencesChangedEvent event, final UUID referenceId, final List<Relation> relations) {
        for (Relation relation : relations) {
            checkFromId(event, referenceId, relation);
            checkToId(event, referenceId, relation);
        }
    }

    private void checkToId(final ReferencesChangedEvent event, final UUID referenceId, final Relation relation) {
        if (relation.getToId().equals(referenceId)) {
            relation.setToId(event.getNewReferenceId());
        }
    }

    private void checkFromId(final ReferencesChangedEvent event, final UUID referenceId, final Relation relation) {
        if (relation.getFromId().equals(referenceId)) {
            relation.setFromId(event.getNewReferenceId());
        }
    }
}
