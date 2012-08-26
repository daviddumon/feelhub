package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.DomainEvent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConceptGroupTranslatedEvent extends DomainEvent {

    public ConceptGroupTranslatedEvent(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptGroupTranslatedEvent ");
        return stringBuilder.toString();
    }

    public CopyOnWriteArrayList<ConceptTranslatedEvent> getConceptTranslatedEvents() {
        return conceptTranslatedEvents;
    }

    public void addIfAbsent(final ConceptTranslatedEvent conceptEvent) {
        conceptTranslatedEvents.addIfAbsent(conceptEvent);
    }

    public void addAllAbsent(final List<ConceptTranslatedEvent> conceptEvents) {
        this.conceptTranslatedEvents.addAllAbsent(conceptEvents);
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private CopyOnWriteArrayList<ConceptTranslatedEvent> conceptTranslatedEvents = Lists.newCopyOnWriteArrayList();
    private UUID referenceId;
}
