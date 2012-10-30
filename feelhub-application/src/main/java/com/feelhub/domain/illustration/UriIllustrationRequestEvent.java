package com.feelhub.domain.illustration;

import com.feelhub.domain.eventbus.DomainEvent;

import java.util.UUID;

public class UriIllustrationRequestEvent extends DomainEvent {

    public UriIllustrationRequestEvent(final UUID referenceId, final String value) {
        this.referenceId = referenceId;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    private final UUID referenceId;
    private String value;
}
