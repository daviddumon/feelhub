package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.real.RealTopic;

public class AlchemyEntityFactory {
    AlchemyEntity create(final NamedEntity namedEntity, final RealTopic realTopic) {
        final AlchemyEntity alchemyEntity = new AlchemyEntity(realTopic.getId());
        alchemyEntity.setCensus(namedEntity.census);
        alchemyEntity.setCiafactbook(namedEntity.ciaFactbook);
        alchemyEntity.setCrunchbase(namedEntity.crunchbase);
        alchemyEntity.setDbpedia(namedEntity.dbpedia);
        alchemyEntity.setFreebase(namedEntity.freebase);
        alchemyEntity.setGeo(namedEntity.geo);
        alchemyEntity.setGeonames(namedEntity.geonames);
        alchemyEntity.setMusicbrainz(namedEntity.musicBrainz);
        alchemyEntity.setOpencyc(namedEntity.opencyc);
        alchemyEntity.setSemanticcrunchbase(namedEntity.semanticCrunchbase);
        alchemyEntity.setSubtype(namedEntity.subType);
        alchemyEntity.setType(namedEntity.type);
        alchemyEntity.setUmbel(namedEntity.umbel);
        alchemyEntity.setWebsite(namedEntity.website);
        alchemyEntity.setYago(namedEntity.yago);
        alchemyEntity.setRelevance(namedEntity.relevance);
        return alchemyEntity;
    }
}
