package com.feelhub.domain.alchemy;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.keyword.Keyword;

public class AlchemyRequestEvent extends DomainEvent {

    public AlchemyRequestEvent(final Keyword uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        return stringBuilder.toString();
    }

    public Keyword getUri() {
        return uri;
    }

    private final Keyword uri;
}
