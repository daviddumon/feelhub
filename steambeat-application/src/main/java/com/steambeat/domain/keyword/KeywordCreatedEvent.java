package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEvent;
import org.joda.time.DateTime;

public class KeywordCreatedEvent implements DomainEvent {

    public KeywordCreatedEvent(final Keyword keyword) {
        this.keyword = keyword;
        this.date = new DateTime();
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Keyword ");
        stringBuilder.append(keyword.getValue() + " " + keyword.getLanguage());
        stringBuilder.append(" created");
        return stringBuilder.toString();
    }

    public Keyword getKeyword() {
        return keyword;
    }

    private Keyword keyword;
    private DateTime date;
}
