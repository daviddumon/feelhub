package com.feelhub.domain.keyword.word;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class Word extends Keyword {

    //mongolink constructor do not delete
    public Word() {
    }

    public Word(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        super(value, feelhubLanguage, topicId);
        translationNeeded = false;
    }

    public boolean isTranslationNeeded() {
        return translationNeeded;
    }

    public void setTranslationNeeded(final boolean translationNeeded) {
        this.translationNeeded = translationNeeded;
    }

    private boolean translationNeeded;
}