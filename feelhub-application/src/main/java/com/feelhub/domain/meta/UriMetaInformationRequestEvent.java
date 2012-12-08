package com.feelhub.domain.meta;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.usable.real.RealTopic;

public class UriMetaInformationRequestEvent extends DomainEvent {

    public UriMetaInformationRequestEvent(final RealTopic realTopic, final String value) {
        this.realTopic = realTopic;
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

    public RealTopic getRealTopic() {
        return realTopic;
    }

    public String getValue() {
        return value;
    }

    private final RealTopic realTopic;
    private final String value;
}
