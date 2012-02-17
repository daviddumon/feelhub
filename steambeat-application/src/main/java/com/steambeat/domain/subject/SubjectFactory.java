package com.steambeat.domain.subject;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.domain.textAnalytics.NamedEntity;

public class SubjectFactory {

    public WebPage newWebPage(final Association association) {
        return new WebPageFactory().newWebPage(association);
    }

    public Concept newConcept(final NamedEntity namedEntity) {
        return new ConceptFactory().newConcept(namedEntity);
    }

}
