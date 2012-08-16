package com.steambeat.domain.translation;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;

public class TranslationDoneEvent extends DomainEvent {

    public TranslationDoneEvent(final Keyword keyword, final String result) {
        super();
        this.keyword = keyword;
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Keyword ");
        stringBuilder.append(keyword.getValue() + " " + keyword.getLanguage());
        stringBuilder.append(" translated into ");
        stringBuilder.append(result);
        return stringBuilder.toString();
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public String getResult() {
        return result;
    }

    private Keyword keyword;
    private String result;
}
