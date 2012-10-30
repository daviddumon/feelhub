package com.feelhub.domain.reference;

import com.google.common.collect.Lists;

import java.util.*;

public class ReferencePatch {

    public ReferencePatch(final UUID newReferenceId) {
        this.newReferenceId = newReferenceId;
    }

    public UUID getNewReferenceId() {
        return newReferenceId;
    }

    public List<UUID> getOldReferenceIds() {
        return oldReferenceIds;
    }

    public void addOldReferenceId(final UUID oldReferenceId) {
        this.oldReferenceIds.add(oldReferenceId);
    }

    final UUID newReferenceId;
    final List<UUID> oldReferenceIds = Lists.newArrayList();
}
