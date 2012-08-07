package com.steambeat.application;

import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.Language;

public class KeywordService {

    public Keyword lookUp(final String value, final Language language) {
        return new Keyword(value, language);
    }
}
