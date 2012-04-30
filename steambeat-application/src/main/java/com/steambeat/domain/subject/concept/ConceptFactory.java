package com.steambeat.domain.subject.concept;

import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class ConceptFactory {

    public Concept newConcept(final AlchemyJsonEntity entity) {
        final Concept concept = new Concept(getGoodText(entity));
        concept.setLanguage(Language.forString(entity.language));
        concept.setType(Type.forString(entity.type));
        concept.setRelevance(entity.relevance);
        final AlchemyJsonDisambiguated disambiguated = entity.disambiguated;
        concept.setSubTypes(disambiguated.subType);
        concept.setWebsite(new Uri(disambiguated.website));
        concept.setGeo(disambiguated.geo);
        return concept;
    }

    private String getGoodText(final AlchemyJsonEntity entity) {
        if (entity.disambiguated.name.isEmpty()) {
            return entity.text;
        }
        return entity.disambiguated.name;
    }

    public Concept lookUpConcept(final UUID subjectId) {
        return (Concept) Repositories.subjects().get(subjectId);
    }
}
