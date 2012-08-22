package com.steambeat.domain.concept;

import com.steambeat.domain.eventbus.DomainEvent;

public class ConceptTranslatedEvent extends DomainEvent {

    public ConceptTranslatedEvent(final Concept concept) {
        super();
        this.concept = concept;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptTranslatedEvent ");
        stringBuilder.append(concept.getKeywords().get(0).getValue());
        return stringBuilder.toString();
    }

    public Concept getConcept() {
        return concept;
    }

    private Concept concept;
}
