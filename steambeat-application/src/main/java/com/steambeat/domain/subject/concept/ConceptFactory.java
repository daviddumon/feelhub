package com.steambeat.domain.subject.concept;

import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.thesaurus.Type;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class ConceptFactory {

    //todo delete
    public Concept newConcept(final AlchemyJsonEntity entity) {
        return null;
    }

    public Concept newConcept(final NamedEntity entity) {
        final Concept concept = new Concept(entity.conceptId);
        concept.setShortDescription(entity.name);
        concept.setDescription(entity.name);
        concept.setType(Type.forString(entity.type));
        concept.setSubTypes(entity.subType);
        concept.setWebsite(new Uri(entity.website));
        concept.setGeo(entity.geo);
        return concept;
    }

    public Concept lookUpConcept(final UUID subjectId) {
        return (Concept) Repositories.subjects().get(subjectId);
    }
}
