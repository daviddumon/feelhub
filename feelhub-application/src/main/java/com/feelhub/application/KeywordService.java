package com.feelhub.application;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;
import org.apache.commons.lang.WordUtils;

import java.util.*;

public class KeywordService {

    @Inject
    public KeywordService(final WordService wordService, final UriService uriService) {
        this.wordService = wordService;
        this.uriService = uriService;
    }

    public Keyword lookUpOrCreate(final String value, final String languageCode) {
        if (KeywordIdentifier.isUri(value)) {
            return uriService.lookUpOrCreate(value);
        } else {
            return wordService.lookUpOrCreate(value, FeelhubLanguage.forString(languageCode));
        }
    }

    public Keyword lookUp(final String value, final FeelhubLanguage feelhubLanguage) {
        checkSize(value);
        final Keyword keyword;
        if (KeywordIdentifier.isUri(value)) {
            keyword = Repositories.keywords().forValueAndLanguage(value, feelhubLanguage);
        } else {
            keyword = Repositories.keywords().forValueAndLanguage(normalizeWord(value), feelhubLanguage);
        }
        if (keyword == null) {
            throw new KeywordNotFound();
        }
        return keyword;
    }

    private void checkSize(final String value) {
        // Note : there is not much sens in keeping words smaller than 3
        // but as we use a translation to reference keywords between eachother
        // we have to accept anything not empty
        // Only exception to this rule is the "world" keyword that aggragates everything else
        if (value.isEmpty()) {
            throw new BadValueException();
        }
    }

    public Keyword lookUp(final UUID topicId, final FeelhubLanguage language) {
        final Keyword keyword;
        final List<Keyword> keywords = Repositories.keywords().forTopicId(topicId);
        if (!keywords.isEmpty()) {
            keyword = getGoodKeyword(keywords, language);
        } else {
            // it should never happens!
            keyword = new Word("?", language, topicId);
        }
        return keyword;
    }

    private Keyword getGoodKeyword(final List<Keyword> keywords, final FeelhubLanguage feelhubLanguage) {
        Keyword referenceKeyword = null;
        for (final Keyword keyword : keywords) {
            if (keyword.getLanguage().equals(feelhubLanguage)) {
                return keyword;
            } else if (keyword.getLanguage().equals(FeelhubLanguage.reference())) {
                referenceKeyword = keyword;
            }
        }
        if (referenceKeyword != null) {
            return referenceKeyword;
        } else {
            return keywords.get(0);
        }
    }

    private String normalizeWord(String value) {
        value = value.toLowerCase();
        value = WordUtils.capitalize(value);
        return value;
    }

    private final WordService wordService;
    private final UriService uriService;
}
