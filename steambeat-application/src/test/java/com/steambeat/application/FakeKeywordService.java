package com.steambeat.application;

import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.FakeTranslator;
import com.steambeat.domain.uri.*;

public class FakeKeywordService extends KeywordService {

    public FakeKeywordService() {
        super(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Override
    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        final ReferenceService referenceService = new ReferenceService(new ReferenceFactory());
        return new Keyword(value, steambeatLanguage, referenceService.newReference().getId());
    }
}
