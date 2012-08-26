package com.steambeat.domain.reference;

import java.util.UUID;

public class ConceptReferencesChangedEvent extends ReferencesChangedEvent {

    public ConceptReferencesChangedEvent(final UUID referenceId) {
        super(referenceId);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptReferencesChangedEvent ");
        stringBuilder.append(getReferenceIds().size());
        return stringBuilder.toString();
    }
}
