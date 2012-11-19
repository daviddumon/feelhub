package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final Topic topic, final String value) {
        this.topic = topic;
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

    public Topic getTopic() {
        return topic;
    }

    public String getValue() {
        return value;
    }

    private Topic topic;
    private String value;
}
