package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.usable.real.RealTopic;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final RealTopic realTopic, final String value) {
        this.realTopic = realTopic;
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
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
