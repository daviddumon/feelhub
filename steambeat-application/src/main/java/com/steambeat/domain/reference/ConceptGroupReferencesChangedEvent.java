package com.steambeat.domain.reference;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConceptGroupReferencesChangedEvent extends DomainEvent {

    public ConceptGroupReferencesChangedEvent(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptGroupReferencesChangedEvent ");
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<ConceptReferencesChangedEvent> getConceptReferencesChangedEvents() {
        return conceptReferencesChangedEvents;
    }

    public void addIfAbsent(final ConceptReferencesChangedEvent conceptEvent) {
        conceptReferencesChangedEvents.addIfAbsent(conceptEvent);
    }

    public void addAllAbsent(final List<ConceptReferencesChangedEvent> conceptEvents) {
        this.conceptReferencesChangedEvents.addAllAbsent(conceptEvents);
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private CopyOnWriteArrayList<ConceptReferencesChangedEvent> conceptReferencesChangedEvents = Lists.newCopyOnWriteArrayList();

    private UUID referenceId;
}
