package com.steambeat.domain.subject;

import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.domain.textAnalytics.NamedEntity;

public class SubjectFactory {

    public WebPage createWebPage(final Association association) {
        return new WebPageFactory().newWebPage(association);
    }

    public Concept createConcept(final NamedEntity namedEntity) {
        return new ConceptFactory().newConcept(namedEntity);
    }

}
