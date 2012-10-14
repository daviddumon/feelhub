package com.steambeat.domain.opinion;

import com.steambeat.repositories.Repositories;

import java.util.*;

public class OpinionManager {

    public void migrate(final UUID newReferenceId, final List<UUID> oldReferenceIds) {
        for (final UUID oldReferenceId : oldReferenceIds) {
            final List<Opinion> opinionsForReferenceId = Repositories.opinions().forReferenceId(oldReferenceId);
            if (!opinionsForReferenceId.isEmpty()) {
                for (final Opinion opinion : opinionsForReferenceId) {
                    for (final Judgment judgment : opinion.getJudgments()) {
                        if (judgment.getReferenceId().equals(oldReferenceId)) {
                            judgment.setReferenceId(newReferenceId);
                        }
                    }
                }
            }
        }
    }
}
