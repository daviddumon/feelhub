package com.steambeat.domain.alchemy;

import com.steambeat.domain.eventbus.DomainEvent;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("AlchemyRequestEvent");
        return stringBuilder.toString();
    }

    public String getUri() {
        return uri;
    }

    private final String uri;
}
