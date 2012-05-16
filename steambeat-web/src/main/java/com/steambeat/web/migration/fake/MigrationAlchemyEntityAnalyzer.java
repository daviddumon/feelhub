package com.steambeat.web.migration.fake;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.subject.webpage.WebPage;

public class MigrationAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public MigrationAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new AlchemyLink()), new AssociationService(new MigrationUriPathResolver()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
