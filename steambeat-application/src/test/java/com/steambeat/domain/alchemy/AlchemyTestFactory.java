package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;

import java.util.*;

public class AlchemyTestFactory {

    public AlchemyEntity newAlchemyEntityEntity(final UUID referenceId) {
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

    public AlchemyAnalysis newAlchemyAnalysis(final Reference reference) {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.none(), reference);
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(keyword);
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }

    public AlchemyAnalysis newAlchemyAnalysis() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(keyword);
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }
}
