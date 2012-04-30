package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.SessionProvider;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new AlchemyJsonEntityProvider(new FakeJsonAlchemyLink()), new SessionProvider());
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
