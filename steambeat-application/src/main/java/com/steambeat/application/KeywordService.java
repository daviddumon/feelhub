package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.Language;

public class KeywordService {

    @Inject
    public KeywordService(final KeywordFactory keywordFactory) {
        this.keywordFactory = keywordFactory;
    }

    public Keyword lookUp(final String value, final Language language) {
        return keywordFactory.createKeyword(value, language);
    }

    private KeywordFactory keywordFactory;
}
