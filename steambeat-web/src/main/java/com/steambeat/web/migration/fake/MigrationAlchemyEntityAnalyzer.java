package com.steambeat.web.migration.fake;

import com.steambeat.domain.analytics.alchemy.*;
import com.steambeat.domain.subject.webpage.WebPage;

public class MigrationAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public MigrationAlchemyEntityAnalyzer() {
        super(new AlchemyJsonEntityProvider(new AlchemyLink()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
