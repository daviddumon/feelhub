package com.steambeat.domain.relation;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AlchemyRelationBinder {

    @Inject
    public AlchemyRelationBinder(final RelationBuilder relationBuilder) {
        this.relationBuilder = relationBuilder;
    }

    public void bind(final UUID mainReferenceId, final HashMap<UUID, Double> referenceIds) {
        final Reference mainReference = loadReference(mainReferenceId);
        List<Reference> references = Lists.newArrayList();
        connectAllReferencesToMainReferenceWithScore(referenceIds, mainReference, references);
        connectAllReferencesToThemselves(references);
    }

    private void connectAllReferencesToThemselves(final List<Reference> references) {
        for (int i = 0; i < references.size(); i++) {
            final Reference currentReference = references.get(i);
            connectReference(currentReference, i + 1, references);
        }
    }

    private void connectReference(final Reference currentReference, final int beginningIndex, final List<Reference> references) {
        for (int i = beginningIndex; i < references.size(); i++) {
            relationBuilder.connectTwoWays(currentReference, references.get(i));
        }
    }

    private void connectAllReferencesToMainReferenceWithScore(final HashMap<UUID, Double> referenceIds, final Reference mainReference, final List<Reference> references) {
        for (Map.Entry<UUID, Double> entry : referenceIds.entrySet()) {
            final UUID currentReferenceId = entry.getKey();
            final Double score = entry.getValue();
            final Reference currentReference = loadReference(currentReferenceId);
            relationBuilder.connectTwoWays(currentReference, mainReference, score);
            references.add(currentReference);
        }
    }

    private Reference loadReference(final UUID currentReferenceId) {
        return Repositories.references().get(currentReferenceId);
    }

    private final RelationBuilder relationBuilder;
}