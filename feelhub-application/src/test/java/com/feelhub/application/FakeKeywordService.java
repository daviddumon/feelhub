package com.feelhub.application;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.reference.ReferenceFactory;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;

public class FakeKeywordService extends KeywordService {

    public FakeKeywordService() {
        super(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver()));
    }

    @Override
    public Keyword createKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        final ReferenceService referenceService = new ReferenceService(new ReferenceFactory());
        return new Keyword(value, feelhubLanguage, referenceService.newReference().getId());
    }
}
