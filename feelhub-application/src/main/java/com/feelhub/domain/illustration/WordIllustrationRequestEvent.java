package com.feelhub.domain.illustration;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.keyword.word.Word;

import java.util.UUID;

public class WordIllustrationRequestEvent extends DomainEvent {

    public WordIllustrationRequestEvent(final Word word, final String type) {
        this.topicId = word.getTopicId();
        this.value = word.getValue();
        this.type = type;
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

    public String getType() {
        return type;
    }

    private final UUID topicId;
    private final String value;
    private final String type;
}
