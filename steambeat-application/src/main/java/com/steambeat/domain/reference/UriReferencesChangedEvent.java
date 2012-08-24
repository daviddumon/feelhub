package com.steambeat.domain.reference;

import java.util.UUID;

public class UriReferencesChangedEvent extends ReferencesChangedEvent {

    public UriReferencesChangedEvent(final UUID referenceId) {
        super(referenceId);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("UriReferencesChangedEvent ");
        stringBuilder.append(referenceIds.size());
        return stringBuilder.toString();
    }
}
