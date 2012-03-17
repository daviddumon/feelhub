package com.steambeat.domain.subject;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SubjectFactory {

    @Inject
    public SubjectFactory(final WebPageFactory webPageFactory) {
        this.webPageFactory = webPageFactory;
    }

    public WebPage newWebPage(final Association association) {
        return webPageFactory.newWebPage(association);
    }

    public Concept newConcept(final NamedEntity namedEntity) {
        return new ConceptFactory().newConcept(namedEntity);
    }

    public WebPage lookUpWebpage(final UUID subjectId) {
        return (WebPage) Repositories.subjects().get(subjectId);
    }

    private WebPageFactory webPageFactory;
}
