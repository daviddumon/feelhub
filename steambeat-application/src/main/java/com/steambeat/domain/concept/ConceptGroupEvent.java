package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConceptGroupEvent extends DomainEvent {

    public ConceptGroupEvent(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptGroupEvent ");
        stringBuilder.append(conceptEvents.size());
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<ConceptEvent> getConceptEvents() {
        return conceptEvents;
    }

    public void addIfAbsent(final ConceptEvent conceptEvent) {
        conceptEvents.addIfAbsent(conceptEvent);
    }

    public void addAllAbsent(final List<ConceptEvent> conceptEvents) {
        this.conceptEvents.addAllAbsent(conceptEvents);
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private final CopyOnWriteArrayList<ConceptEvent> conceptEvents = Lists.newCopyOnWriteArrayList();
    private final UUID referenceId;
}
