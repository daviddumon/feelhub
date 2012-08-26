package com.steambeat.domain.uri;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

public class UriEvent extends DomainEvent {

    public UriEvent(final Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("UriEvent ");
        stringBuilder.append(keyword.getValue());
        return stringBuilder.toString();
    }

    public Keyword getKeyword() {
        return keyword;
    }

    private final Keyword keyword;
}
