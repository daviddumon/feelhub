package com.steambeat.domain.illustration;

import com.steambeat.domain.eventbus.DomainEvent;

import java.util.UUID;

public class ConceptIllustrationRequestEvent extends DomainEvent {

    public ConceptIllustrationRequestEvent(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("ConceptIllustrationRequestEvent created");
        return stringBuilder.toString();
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private UUID referenceId;
}
