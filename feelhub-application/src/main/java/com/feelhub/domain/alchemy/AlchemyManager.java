package com.feelhub.domain.alchemy;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class AlchemyManager {

    public void merge(final ReferencePatch referencePatch) {
        mergeAlchemyEntities(referencePatch);
        removeDuplicateAlchemyEntities(referencePatch.getNewReferenceId());
        mergeAlchemyAnalysis(referencePatch);
        removeDuplicateAlchemyAnalysis(referencePatch.getNewReferenceId());
    }

    private void mergeAlchemyEntities(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            final List<AlchemyEntity> entities = Repositories.alchemyEntities().forReferenceId(oldReferenceId);
            if (!entities.isEmpty()) {
                for (final AlchemyEntity entity : entities) {
                    entity.setNewReferenceId(referencePatch.getNewReferenceId());
                }
            }
        }
    }

    private void removeDuplicateAlchemyEntities(final UUID newReferenceId) {
        final List<AlchemyEntity> entities = Repositories.alchemyEntities().forReferenceId(newReferenceId);
        if (entities.size() > 1) {
            for (int i = 1; i < entities.size(); i++) {
                Repositories.alchemyEntities().delete(entities.get(i));
            }
        }
    }

    private void mergeAlchemyAnalysis(final ReferencePatch referencePatch) {
        for (final UUID oldRefenceId : referencePatch.getOldReferenceIds()) {
            final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forReferenceId(oldRefenceId);
            if (!alchemyAnalysisList.isEmpty()) {
                for (final AlchemyAnalysis analysis : alchemyAnalysisList) {
                    analysis.setNewReferenceId(referencePatch.getNewReferenceId());
                }
            }
        }
    }

    private void removeDuplicateAlchemyAnalysis(final UUID newReferenceId) {
        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forReferenceId(newReferenceId);
        if (alchemyAnalysisList.size() > 1) {
            for (int i = 1; i < alchemyAnalysisList.size(); i++) {
                Repositories.alchemyAnalysis().delete(alchemyAnalysisList.get(i));
            }
        }
    }
}
