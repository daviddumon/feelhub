package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;

public class KeywordFactory {

    public Keyword createKeyword(final String value, final Language language) {
        final Keyword keyword = new Keyword(value, language, UUID.randomUUID());
        final KeywordCreatedEvent keywordCreatedEvent = new KeywordCreatedEvent(keyword);
        DomainEventBus.INSTANCE.post(keywordCreatedEvent);
        return keyword;
    }
}
