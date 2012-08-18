package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReferencesChangedEvent extends DomainEvent {

    public ReferencesChangedEvent(final UUID reference) {
        this.newReference = reference;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("References changed event");
        return stringBuilder.toString();
    }

    public List<UUID> getReferences() {
        return references;
    }

    public void addIfAbsent(final UUID reference) {
        this.references.addIfAbsent(reference);
    }

    public UUID getNewReference() {
        return newReference;
    }

    private CopyOnWriteArrayList<UUID> references = Lists.newCopyOnWriteArrayList();
    private UUID newReference;
}
