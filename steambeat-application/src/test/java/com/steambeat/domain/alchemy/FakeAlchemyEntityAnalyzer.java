package com.steambeat.domain.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.subject.concept.FakeConceptFactory;
import com.steambeat.test.*;

public class FakeAlchemyEntityAnalyzer extends AlchemyEntityAnalyzer {

    public FakeAlchemyEntityAnalyzer() {
        super(new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()))), new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()), new FakeConceptFactory(new FakeBingLink()));
    }

    @Override
    public void analyze(final Uri uri) {

    }
}
