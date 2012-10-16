package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AlchemyTestFactory {

    public AlchemyEntity newAlchemyEntity(final UUID referenceId) {
        final AlchemyEntity alchemyEntity = new AlchemyEntity(referenceId);
        alchemyEntity.setCensus("census");
        alchemyEntity.setCiafactbook("ciafactbook");
        alchemyEntity.setCrunchbase("crunchbase");
        alchemyEntity.setDbpedia("dbpedia");
        alchemyEntity.setFreebase("freebase");
        alchemyEntity.setGeo("geo");
        alchemyEntity.setGeonames("geonames");
        alchemyEntity.setMusicbrainz("musicbrainz");
        alchemyEntity.setOpencyc("opencyc");
        alchemyEntity.setSemanticcrunchbase("crunchbase");
        final List<String> subTypes = Lists.newArrayList();
        subTypes.add("sub1");
        subTypes.add("sub2");
        alchemyEntity.setSubtype(subTypes);
        alchemyEntity.setType("type");
        alchemyEntity.setUmbel("umbel");
        alchemyEntity.setWebsite("website");
        alchemyEntity.setYago("yago");
        alchemyEntity.setRelevance(1.0);
        Repositories.alchemyEntities().add(alchemyEntity);
        return alchemyEntity;
    }
}
