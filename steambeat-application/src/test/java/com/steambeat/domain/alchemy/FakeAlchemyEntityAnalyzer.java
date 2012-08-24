package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder()), new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Override
    public void analyze(final String uri) {

    }
}
