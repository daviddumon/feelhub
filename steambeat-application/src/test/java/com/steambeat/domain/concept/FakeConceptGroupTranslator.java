package com.steambeat.domain.concept;

import com.steambeat.application.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.repositories.fakeRepositories.FakeSessionProvider;

public class FakeConceptGroupTranslator extends ConceptGroupTranslator {

    public FakeConceptGroupTranslator() {
        super(new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())), new FakeSessionProvider());
    }

    @Override
    protected String translateKeywordToEnglish(final Keyword keyword) throws Exception {
        return keyword.getValue() + "english";
    }
}
