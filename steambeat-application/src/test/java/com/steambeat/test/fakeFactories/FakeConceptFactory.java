package com.steambeat.test.fakeFactories;

import com.steambeat.domain.subject.concept.ConceptFactory;
import com.steambeat.test.FakeBingLink;

public class FakeConceptFactory extends ConceptFactory {

    public FakeConceptFactory() {
        super(new FakeBingLink());
    }


}
