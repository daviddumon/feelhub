package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.repositories.fakeRepositories.FakeSessionProvider;

public class FakeAlchemyAnalyzer extends AlchemyAnalyzer {

    public FakeAlchemyAnalyzer() {
        super(new FakeSessionProvider(), new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder()), new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }
}
