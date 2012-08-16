package com.steambeat.domain.alchemy;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.subject.concept.FakeConceptFactory;
import com.steambeat.domain.topic.TopicFactory;
import com.steambeat.domain.uri.Uri;
import com.steambeat.test.FakeBingLink;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new TopicFactory()))), new KeywordService(new KeywordFactory(), new TopicFactory()), new FakeConceptFactory(new FakeBingLink()));
    }

    @Override
    public void analyze(final Uri uri) {

    }
}
