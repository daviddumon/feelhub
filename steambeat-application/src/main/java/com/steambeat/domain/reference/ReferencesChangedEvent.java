package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ReferencesChangedEvent extends DomainEvent {

    public ReferencesChangedEvent(final UUID referenceId) {
        this.newReferenceId = referenceId;
    }

    public CopyOnWriteArrayList<UUID> getReferenceIds() {
        return referenceIds;
    }

    public void addReferenceToChange(final UUID reference) {
        this.referenceIds.addIfAbsent(reference);
    }

    public UUID getNewReferenceId() {
        return newReferenceId;
    }

    protected UUID newReferenceId;
    protected CopyOnWriteArrayList<UUID> referenceIds = Lists.newCopyOnWriteArrayList();
}
