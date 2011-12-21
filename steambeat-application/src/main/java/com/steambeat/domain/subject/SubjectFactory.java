package com.steambeat.domain.subject;

import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.concept.ConceptFactory;
import com.steambeat.domain.subject.webpage.Association;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.domain.subject.webpage.WebPageFactory;
import com.steambeat.domain.textAnalytics.NamedEntity;

public class SubjectFactory {
    public WebPage createWebPage(Association association) {
        return new WebPageFactory().newWebPage(association);
    }

    public Concept createConcept(NamedEntity namedEntity) {
        return new ConceptFactory().newConcept(namedEntity);
    }

}
