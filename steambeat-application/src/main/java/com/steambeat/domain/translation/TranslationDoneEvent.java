package com.steambeat.domain.translation;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.subject.concept.Concept;

public class TranslationDoneEvent extends DomainEvent {

    public TranslationDoneEvent(final Concept concept) {
        super();
        this.concept = concept;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Translation done for ");
        stringBuilder.append(concept.getKeywords().get(0).getValue());
        return stringBuilder.toString();
    }

    public Concept getConcept() {
        return concept;
    }

    private Concept concept;
}
