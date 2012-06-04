package com.steambeat.domain.subject;

import com.google.inject.Inject;
import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class SubjectFactory {

    @Inject
    public SubjectFactory(final WebPageFactory webPageFactory, final ConceptFactory conceptFactory) {
        this.webPageFactory = webPageFactory;
        this.conceptFactory = conceptFactory;
    }

    public WebPage newWebPage(final Association association) {
        return webPageFactory.newWebPage(association);
    }

    public Concept newConcept(final NamedEntity namedEntity) {
        return conceptFactory.newConcept(namedEntity);
    }

    public WebPage lookUpWebpage(final UUID subjectId) {
        return (WebPage) Repositories.subjects().get(subjectId);
    }

    public Subject lookUpSubject(final UUID subjectId) {
        return Repositories.subjects().get(subjectId);
    }

    public Concept lookUpConcept(final UUID subjectId) {
        return conceptFactory.lookUpConcept(subjectId);
    }

    private final WebPageFactory webPageFactory;
    private final ConceptFactory conceptFactory;
}
