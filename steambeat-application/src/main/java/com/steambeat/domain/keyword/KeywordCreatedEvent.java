package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEvent;

public class KeywordCreatedEvent extends DomainEvent {

    public KeywordCreatedEvent(final Keyword keyword) {
        super();
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("KeywordCreatedEvent ");
        stringBuilder.append(keyword.getValue() + " " + keyword.getLanguage().getCode());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public Keyword getKeyword() {
        return keyword;
    }

    private Keyword keyword;
}
