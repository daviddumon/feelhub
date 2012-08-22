package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

import java.util.UUID;

public class KeywordFactory {

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage, final UUID referenceId) {
        final Keyword keyword = new Keyword(value, steambeatLanguage, referenceId);
        return keyword;
    }
}
