package com.steambeat.domain.alchemy;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.subject.uri.Uri;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new ReferenceFactory()))), new KeywordService(new KeywordFactory(), new ReferenceFactory()));
    }

    @Override
    public void analyze(final Uri uri) {

    }
}
