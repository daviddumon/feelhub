package com.steambeat.test.testFactories;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.alchemy.readmodel.*;

import java.util.List;

public class AlchemyFactoryForTest {

    public List<AlchemyJsonEntity> entities(final int quantity) {
        List<AlchemyJsonEntity> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            result.add(createEntity(i));
        }
        return result;
    }

    private AlchemyJsonEntity createEntity(final int i) {
        final AlchemyJsonEntity entity = new AlchemyJsonEntity();
        entity.text = "text" + i;
        entity.language = "english";
        entity.type = "type" + i;
        entity.relevance = 0.5;
        entity.count = 1;
        entity.disambiguated = new AlchemyJsonDisambiguated();
        entity.disambiguated.name = "name" + i;
        List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        entity.disambiguated.subType = subtypes;
        entity.disambiguated.website = "website" + i;
        entity.disambiguated.geo = "geo" + i;
        entity.disambiguated.dbpedia = "dbpedia" + i;
        entity.disambiguated.yago = "yago" + i;
        entity.disambiguated.opencyc = "opencyc" + i;
        entity.disambiguated.umbel = "umbel" + i;
        entity.disambiguated.freebase = "freebase" + i;
        entity.disambiguated.ciaFactbook = "ciafactbook" + i;
        entity.disambiguated.census = ""; // for testing purpose
        entity.disambiguated.geonames = "geonames" + i;
        entity.disambiguated.musicBrainz = "musicbrainz" + i;
        entity.disambiguated.crunchbase = "crunchbase" + i;
        entity.disambiguated.semanticCrunchbase = "semanticcrunchbase" + i;
        return entity;
    }

    public List<AlchemyJsonEntity> entitiesWithoutDisambiguated(final int quantity) {
        List<AlchemyJsonEntity> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            result.add(createEntityWithoutDisambiguated(i));
        }
        return result;
    }

    private AlchemyJsonEntity createEntityWithoutDisambiguated(final int i) {
        final AlchemyJsonEntity entity = new AlchemyJsonEntity();
        entity.text = "text" + i;
        entity.language = "english";
        entity.type = "type" + i;
        entity.relevance = 0.5;
        entity.count = 1;
        return entity;
    }
}
