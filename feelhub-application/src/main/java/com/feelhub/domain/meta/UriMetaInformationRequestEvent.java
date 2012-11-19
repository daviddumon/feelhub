package com.feelhub.domain.meta;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

public class UriMetaInformationRequestEvent extends DomainEvent {

    public UriMetaInformationRequestEvent(final Topic topic, final String value) {
        this.topic = topic;
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + "created");
        return stringBuilder.toString();
    }

    public Topic getTopic() {
        return topic;
    }

    public String getValue() {
        return value;
    }

    private final Topic topic;
    private final String value;
}
