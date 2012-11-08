package com.feelhub.domain.feeling;

import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class FeelingManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            final List<Feeling> feelingsForOldReferenceId = Repositories.feelings().forReferenceId(oldReferenceId);
            if (!feelingsForOldReferenceId.isEmpty()) {
                for (final Feeling feeling : feelingsForOldReferenceId) {
                    for (final Sentiment sentiment : feeling.getSentiments()) {
                        if (sentiment.getReferenceId().equals(oldReferenceId)) {
                            sentiment.setReferenceId(referencePatch.getNewReferenceId());
                        }
                    }
                }
            }
        }
    }
}
