package com.steambeat.domain.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.*;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(),new NamedEntityBuilder(new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()))), new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()));
    }

    @Override
    public void analyze(final WebPage webpage) {

    }
}
