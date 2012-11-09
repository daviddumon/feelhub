package com.feelhub.domain.alchemy;

import com.feelhub.application.*;
import com.feelhub.domain.keyword.KeywordFactory;
import com.feelhub.domain.relation.*;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.fakeRepositories.FakeSessionProvider;

public class FakeAlchemyAnalyzer extends AlchemyAnalyzer {

    public FakeAlchemyAnalyzer() {
        super(new FakeSessionProvider(), new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder()), new KeywordService(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())), new AlchemyRelationBinder(new RelationBuilder(new RelationFactory())));
    }
}
