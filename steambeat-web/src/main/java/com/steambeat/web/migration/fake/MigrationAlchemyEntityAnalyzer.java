package com.steambeat.web.migration.fake;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.domain.translation.MicrosoftTranslator;

public class MigrationAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public MigrationAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new AlchemyLink(), new NamedEntityBuilder(new AssociationService(new MigrationUriPathResolver(), new MicrosoftTranslator()))), new AssociationService(new MigrationUriPathResolver(), new MicrosoftTranslator()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
