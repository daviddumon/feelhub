package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;

public class KeywordFactory {

    public Keyword createKeyword(final String value, final Language language, final UUID referenceId) {
        final Keyword keyword = new Keyword(value, language, referenceId);
        final KeywordCreatedEvent keywordCreatedEvent = new KeywordCreatedEvent(keyword);
        DomainEventBus.INSTANCE.post(keywordCreatedEvent);
        return keyword;
    }
}
