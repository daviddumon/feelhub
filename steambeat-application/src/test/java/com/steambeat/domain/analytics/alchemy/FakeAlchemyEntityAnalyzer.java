package com.steambeat.domain.analytics.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.FakeUriPathResolver;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new AlchemyJsonEntityProvider(new FakeJsonAlchemyLink()), new AssociationService(new FakeUriPathResolver()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
