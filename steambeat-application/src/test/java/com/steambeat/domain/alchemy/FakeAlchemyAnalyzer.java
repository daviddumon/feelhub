package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.translation.FakeTranslator;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.fakeRepositories.FakeSessionProvider;

public class FakeAlchemyAnalyzer extends AlchemyAnalyzer {

    public FakeAlchemyAnalyzer() {
        super(new FakeSessionProvider(), new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder()), new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())), new AlchemyRelationBinder(new RelationBuilder(new RelationFactory())));
    }
}
