package com.steambeat.domain.alchemy;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final Keyword uri) {
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

    public Keyword getUri() {
        return uri;
    }

    private final Keyword uri;
}
