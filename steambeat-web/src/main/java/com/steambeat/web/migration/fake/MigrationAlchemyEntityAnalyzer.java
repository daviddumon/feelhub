package com.steambeat.web.migration.fake;

import com.steambeat.domain.analytics.alchemy.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.SessionProvider;

public class MigrationAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public MigrationAlchemyEntityAnalyzer() {
        super(new AlchemyJsonEntityProvider(new AlchemyLink()), new SessionProvider());
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
