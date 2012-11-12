package com.feelhub.domain.keyword;

import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.UUID;

public class KeywordFactory {

    public Word createWord(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        return new Word(value, feelhubLanguage, topicId);
    }

    public Uri createUri(final String value, final UUID topicId) {
        return new Uri(value, topicId);
    }

    public World createWorld(final UUID topicId) {
        return new World(topicId);
    }
}
