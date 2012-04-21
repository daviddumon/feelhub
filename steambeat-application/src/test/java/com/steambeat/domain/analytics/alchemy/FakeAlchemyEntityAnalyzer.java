package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new AlchemyJsonEntityProvider(new FakeJsonAlchemyLink()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
