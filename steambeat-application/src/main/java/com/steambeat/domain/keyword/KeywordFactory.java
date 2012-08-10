package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;

public class KeywordFactory {

    public Keyword createKeyword(final String value, final Language language) {
        return new Keyword(value, language, UUID.randomUUID());
    }
}
