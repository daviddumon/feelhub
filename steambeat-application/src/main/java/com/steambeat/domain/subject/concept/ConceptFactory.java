package com.steambeat.domain.subject.concept;

import com.steambeat.domain.analytics.alchemy.NamedEntity;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;

public class ConceptFactory {

    public Concept newConcept(final NamedEntity namedEntity) {
        final Concept concept = new Concept(namedEntity.text);
        concept.setLanguage(Language.forString(namedEntity.language));
        concept.setType(Type.forString(namedEntity.type));
        concept.setRelevance(namedEntity.relevance);
        concept.setCount(namedEntity.count);
        concept.setName(namedEntity.name);
        concept.setSubTypes(namedEntity.subTypes);
        concept.setWebsite(new Uri(namedEntity.website));
        concept.setGeo(namedEntity.geo);
        concept.setDbpedia(new Uri(namedEntity.dbpedia));
        concept.setYago(new Uri(namedEntity.yago));
        concept.setOpencyc(new Uri(namedEntity.opencyc));
        concept.setUmbel(new Uri(namedEntity.umbel));
        concept.setFreebase(new Uri(namedEntity.freebase));
        concept.setCiaFactbook(new Uri(namedEntity.ciaFactbook));
        concept.setCensus(new Uri(namedEntity.census));
        concept.setGeonames(new Uri(namedEntity.geonames));
        concept.setMusicBrainz(new Uri(namedEntity.musicBrainz));
        concept.setCrunchbase(new Uri(namedEntity.crunchbase));
        concept.setSemanticCrunchbase(new Uri(namedEntity.semanticCrunchbase));
        return concept;
    }
}
