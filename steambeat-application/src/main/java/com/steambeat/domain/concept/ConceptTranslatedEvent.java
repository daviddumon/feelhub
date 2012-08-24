package com.steambeat.domain.concept;

import com.steambeat.domain.reference.ReferencesToChangeEvent;

public class ConceptTranslatedEvent extends ReferencesToChangeEvent {

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptTranslatedEvent ");
        stringBuilder.append(keywords.size());
        return stringBuilder.toString();
    }
}
