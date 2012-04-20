package com.steambeat.domain.subject;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyXmlEntity;
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

    public Concept newConcept(final AlchemyXmlEntity alchemyXmlEntity) {
        return new ConceptFactory().newConcept(alchemyXmlEntity);
    }

    public WebPage lookUpWebpage(final UUID subjectId) {
        return (WebPage) Repositories.subjects().get(subjectId);
    }

    public Subject lookUpSubject(final UUID subjectId) {
        return Repositories.subjects().get(subjectId);
    }

    private final WebPageFactory webPageFactory;
}
