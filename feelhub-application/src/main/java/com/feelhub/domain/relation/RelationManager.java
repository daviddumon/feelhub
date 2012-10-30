package com.feelhub.domain.relation;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class RelationManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            final List<Relation> relations = Repositories.relations().forReferenceId(oldReferenceId);
            if (!relations.isEmpty()) {
                migrateRelations(referencePatch.getNewReferenceId(), oldReferenceId, relations);
            }
        }
    }

    private void migrateRelations(final UUID newReferenceId, final UUID oldReferenceId, final List<Relation> relations) {
        for (final Relation relation : relations) {
            checkFromId(newReferenceId, oldReferenceId, relation);
            checkToId(newReferenceId, oldReferenceId, relation);
        }
    }

    private void checkFromId(final UUID newReferenceId, final UUID oldReferenceId, final Relation relation) {
        if (relation.getFromId().equals(oldReferenceId)) {
            if (relation.getToId().equals(newReferenceId)) {
                Repositories.relations().delete(relation);
            } else {
                final Relation relationFound = Repositories.relations().lookUp(newReferenceId, relation.getToId());
                if (relationFound != null) {
                    relationFound.addWeight(relation.getWeight());
                    Repositories.relations().delete(relation);
                } else {
                    relation.setFromId(newReferenceId);
                }
            }
        }
    }

    private void checkToId(final UUID newReference, final UUID oldReferenceId, final Relation relation) {
        if (relation.getToId().equals(oldReferenceId)) {
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
}
