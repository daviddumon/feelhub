package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "value";
        final Language language = Language.forString("english");
        final Keyword keyword = new Keyword(value, language, UUID.randomUUID());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public Keyword newKeyword(final String value, final Language language) {
        final Keyword keyword = new Keyword(value, language, UUID.randomUUID());
        Repositories.keywords().add(keyword);
        return keyword;
    }
}
