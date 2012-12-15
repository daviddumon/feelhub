package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.http.HttpTopic;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final HttpTopic httpTopic) {
        this.httpTopic = httpTopic;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        return stringBuilder.toString();
    }

    public HttpTopic getHttpTopic() {
        return httpTopic;
    }

    private final HttpTopic httpTopic;
}
