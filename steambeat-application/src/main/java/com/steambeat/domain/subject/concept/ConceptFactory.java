package com.steambeat.domain.subject.concept;

import com.google.inject.Inject;
import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.bingsearch.BingLink;
import com.steambeat.domain.thesaurus.Type;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class ConceptFactory {

    @Inject
    public ConceptFactory(final BingLink bingLink) {
        this.bingLink = bingLink;
    }

    public Concept newConcept(final NamedEntity entity) {
        final Concept concept = new Concept(entity.conceptId);
        concept.setShortDescription(entity.name);
        concept.setDescription(entity.name);
        concept.setType(Type.forString(entity.type));
        concept.setSubTypes(entity.subType);
        concept.setWebsite(new Uri(entity.website));
        concept.setGeo(entity.geo);
        scrap(concept);
        return concept;
    }

    private void scrap(final Concept concept) {

    }

    public Concept lookUpConcept(final UUID subjectId) {
        return (Concept) Repositories.subjects().get(subjectId);
    }

    private BingLink bingLink;
}
