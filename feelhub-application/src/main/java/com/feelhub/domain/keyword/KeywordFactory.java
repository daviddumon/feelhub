package com.feelhub.domain.keyword;

import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class KeywordFactory {

    public Keyword createKeyword(final String value, final FeelhubLanguage feelhubLanguage, final UUID referenceId) {
        final Keyword keyword = new Keyword(value, feelhubLanguage, referenceId);
        return keyword;
    }
}
