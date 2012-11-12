package com.feelhub.application;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.translation.FakeTranslator;

public class FakeKeywordService extends KeywordService {

    public FakeKeywordService() {
        super(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Override
    public Keyword createKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        final TopicService topicService = new TopicService(new TopicFactory());
        return new Word(value, feelhubLanguage, topicService.newTopic().getId());
    }
}
