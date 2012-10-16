package com.steambeat.domain.illustration;

import com.steambeat.domain.eventbus.DomainEvent;

import java.util.UUID;

public class UriIllustrationRequestEvent extends DomainEvent {

    public UriIllustrationRequestEvent(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("UriIllustrationRequestEvent created");
        return stringBuilder.toString();
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    private final UUID referenceId;
}
