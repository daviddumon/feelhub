package com.feelhub.domain.translation;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.inject.Inject;

public class FakeTranslator extends Translator {

    @Inject
    public FakeTranslator(final TopicService topicService) {
        super();
    }

    @Override
    protected String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) {
        return value + "english";
    }
}
