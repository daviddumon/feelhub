package com.steambeat.domain.concept;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.concept.ConceptTranslator;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.ReferenceFactory;

public class FakeConceptTranslator extends ConceptTranslator {

    public FakeConceptTranslator() {
        super(new KeywordService(new KeywordFactory(), new ReferenceFactory()));
    }

    @Override
    protected String translateKeywordToEnglish(final Keyword keyword) throws Exception {
        return keyword.getValue() + "english";
    }
}
