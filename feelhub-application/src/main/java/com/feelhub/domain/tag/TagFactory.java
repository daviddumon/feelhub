package com.feelhub.domain.tag;

import com.feelhub.domain.tag.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class TagFactory {

    public Word createTag(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        return new Word(value, feelhubLanguage, topicId);
    }
}
