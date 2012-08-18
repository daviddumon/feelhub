package com.steambeat.domain.concept;

import com.steambeat.domain.eventbus.DomainEvent;

public class ConceptCreatedEvent extends DomainEvent {

    public ConceptCreatedEvent(final Concept concept) {
        super();
        this.concept = concept;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Concept created");
        return stringBuilder.toString();
    }

    public Concept getConcept() {
        return concept;
    }

    private Concept concept;
}
