package com.steambeat.domain.subject.concept;

import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;

public class ConceptFactory {

    public Concept newConcept(final AlchemyJsonEntity entity) {
        final Concept concept = new Concept(entity.text);
        concept.setLanguage(Language.forString(entity.language));
        concept.setType(Type.forString(entity.type));
        concept.setRelevance(entity.relevance);
        concept.setCount(entity.count);
        final AlchemyJsonDisambiguated disambiguated = entity.disambiguated;
        concept.setName(disambiguated.name);
        concept.setSubTypes(disambiguated.subType);
        concept.setWebsite(new Uri(disambiguated.website));
        concept.setGeo(disambiguated.geo);
        concept.setDbpedia(new Uri(disambiguated.dbpedia));
        concept.setYago(new Uri(disambiguated.yago));
        concept.setOpencyc(new Uri(disambiguated.opencyc));
        concept.setUmbel(new Uri(disambiguated.umbel));
        concept.setFreebase(new Uri(disambiguated.freebase));
        concept.setCiaFactbook(new Uri(disambiguated.ciaFactbook));
        concept.setCensus(new Uri(disambiguated.census));
        concept.setGeonames(new Uri(disambiguated.geonames));
        concept.setMusicBrainz(new Uri(disambiguated.musicBrainz));
        concept.setCrunchbase(new Uri(disambiguated.crunchbase));
        concept.setSemanticCrunchbase(new Uri(disambiguated.semanticCrunchbase));
        return concept;
    }
}
