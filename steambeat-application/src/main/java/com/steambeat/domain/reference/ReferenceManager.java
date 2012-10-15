package com.steambeat.domain.reference;

import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class ReferenceManager {

    public void merge(final ReferencePatch referencePatch) {
        for (final UUID oldReferenceId : referencePatch.getOldReferenceIds()) {
            if (!oldReferenceId.equals(referencePatch.getNewReferenceId())) {
                final Reference oldReference = Repositories.references().get(oldReferenceId);
                oldReference.setActive(false);
                oldReference.setCurrentReferenceId(referencePatch.getNewReferenceId());
            }
        }
    }
}
