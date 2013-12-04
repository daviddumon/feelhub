package com.feelhub.domain.translation;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.inject.Inject;

public class FakeTranslator extends Translator {

    @Inject
    public FakeTranslator(final TopicSearch topicSearch) {
        super();
    }

    @Override
    protected String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) {
        return value + "english";
    }
}
