package com.steambeat.domain.subject.concept;

import com.google.inject.Inject;
import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.scrapers.ConceptScraper;
import com.steambeat.domain.thesaurus.Type;

import java.util.UUID;

public class ConceptFactory {

    @Inject
    public ConceptFactory(final BingLink bingLink) {
        this.bingLink = bingLink;
    }

    public Concept newConcept(final NamedEntity entity) {
        final Concept concept = new Concept(entity.conceptId);
        concept.setType(Type.forString(entity.type));
        concept.setSubTypes(entity.subType);
        concept.setWebsite(new Uri(entity.website));
        concept.setGeo(entity.geo);
        final ConceptScraper scraper = new ConceptScraper();
        scraper.setBingLink(bingLink);
        return concept;
    }

    public Concept lookUpConcept(final UUID subjectId) {
        return null;
    }

    private final BingLink bingLink;
}
