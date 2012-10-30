package com.feelhub.domain.opinion;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class OpinionManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            final List<Opinion> opinionsForOldReferenceId = Repositories.opinions().forReferenceId(oldReferenceId);
            if (!opinionsForOldReferenceId.isEmpty()) {
                for (final Opinion opinion : opinionsForOldReferenceId) {
                    for (final Judgment judgment : opinion.getJudgments()) {
                        if (judgment.getReferenceId().equals(oldReferenceId)) {
                            judgment.setReferenceId(referencePatch.getNewReferenceId());
                        }
                    }
                }
            }
        }
    }
}
