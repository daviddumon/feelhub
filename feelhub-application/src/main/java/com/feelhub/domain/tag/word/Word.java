package com.feelhub.domain.tag.word;

import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class Word extends Tag {

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

    public void setValue(final String value) {
        this.value = value;
    }

    private boolean translationNeeded;
}
