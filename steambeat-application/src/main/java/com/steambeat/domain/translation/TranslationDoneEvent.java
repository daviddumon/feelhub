package com.steambeat.domain.translation;

import com.steambeat.domain.eventbus.DomainEvent;
import com.steambeat.domain.keyword.Keyword;
import org.joda.time.DateTime;

public class TranslationDoneEvent implements DomainEvent {

    public TranslationDoneEvent(final Keyword keyword, final String result) {
        this.keyword = keyword;
        this.result = result;
        this.date = new DateTime();
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public String getResult() {
        return result;
    }

    private DateTime date;
    private Keyword keyword;
    private String result;
}
