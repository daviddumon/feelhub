package com.feelhub.domain.illustration;

import com.feelhub.domain.eventbus.DomainEvent;

import java.util.UUID;

public class ConceptIllustrationRequestEvent extends DomainEvent {

    public ConceptIllustrationRequestEvent(final UUID topicId, final String value) {
        this.topicId = topicId;
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName());
        return stringBuilder.toString();
    }

    public UUID getTopicId() {
        return topicId;
    }

    public String getValue() {
        return value;
    }

    private final UUID topicId;
    private final String value;
}
