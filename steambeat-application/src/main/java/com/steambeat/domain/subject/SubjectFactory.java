package com.steambeat.domain.subject;

import com.google.inject.Inject;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.*;
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

    public Concept newConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        return new ConceptFactory().newConcept(alchemyJsonEntity);
    }

    public WebPage lookUpWebpage(final UUID subjectId) {
        return (WebPage) Repositories.subjects().get(subjectId);
    }

    public Subject lookUpSubject(final UUID subjectId) {
        return Repositories.subjects().get(subjectId);
    }

    public Concept lookUpConcept(final UUID subjectId) {
        return new ConceptFactory().lookUpConcept(subjectId);
    }

    private final WebPageFactory webPageFactory;
}
