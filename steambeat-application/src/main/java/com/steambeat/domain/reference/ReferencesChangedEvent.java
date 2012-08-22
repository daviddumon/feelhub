package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReferencesChangedEvent extends DomainEvent {

    public ReferencesChangedEvent(final UUID referenceId) {
        this.newReferenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ReferencesChangedEvent ");
        stringBuilder.append(referenceIds.size());
        return stringBuilder.toString();
    }

    public List<UUID> getReferenceIds() {
        return referenceIds;
    }

    public void addReferenceToChange(final UUID reference) {
        this.referenceIds.addIfAbsent(reference);
    }

    public UUID getNewReferenceId() {
        return newReferenceId;
    }

    private CopyOnWriteArrayList<UUID> referenceIds = Lists.newCopyOnWriteArrayList();
    private UUID newReferenceId;
}
