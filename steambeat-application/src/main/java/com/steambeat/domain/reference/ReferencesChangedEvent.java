package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReferencesChangedEvent extends DomainEvent {

    public ReferencesChangedEvent(final Reference reference) {
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

    public List<Reference> getReferences() {
        return references;
    }

    public void addIfAbsent(final Reference reference) {
        this.references.addIfAbsent(reference);
    }

    public Reference getNewReference() {
        return newReference;
    }

    private CopyOnWriteArrayList<Reference> references = Lists.newCopyOnWriteArrayList();
    private Reference newReference;
}
