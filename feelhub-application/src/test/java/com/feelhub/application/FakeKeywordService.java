package com.feelhub.application;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;

public class FakeKeywordService extends KeywordService {

    public FakeKeywordService() {
        super(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Override
    public Keyword createKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        final TopicService topicService = new TopicService(new TopicFactory());
        return new Keyword(value, feelhubLanguage, topicService.newTopic().getId());
    }
}
