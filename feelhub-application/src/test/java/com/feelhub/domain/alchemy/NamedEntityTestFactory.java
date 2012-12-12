package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopicType;
import com.google.common.collect.Lists;

import java.util.List;

public class NamedEntityTestFactory {

    public List<NamedEntity> namedEntityWith2Keywords() {
        final NamedEntity entity = new NamedEntity();
        entity.feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        entity.typeReal = RealTopicType.Other;
        final List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        entity.subType = subtypes;
        entity.website = "website";
        entity.geo = "geo";
        entity.relevance = 0.5;
        entity.tags.add("keyword1");
        entity.tags.add("keyword2");
        final List<NamedEntity> result = Lists.newArrayList();
        result.add(entity);
        return result;
    }

    public List<NamedEntity> namedEntityWith1Keyword() {
        final NamedEntity entity = new NamedEntity();
        entity.feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        entity.typeReal = RealTopicType.Other;
        final List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        entity.subType = subtypes;
        entity.website = "website";
        entity.geo = "geo";
        entity.relevance = 0.5;
        entity.tags.add("keyword1");
        final List<NamedEntity> result = Lists.newArrayList();
        result.add(entity);
        return result;
    }

    public List<NamedEntity> namedEntityWith1KeywordWithoutConcept() {
        final NamedEntity entity = new NamedEntity();
        entity.feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        entity.typeReal = RealTopicType.Other;
        final List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        entity.subType = subtypes;
        entity.website = "website";
        entity.geo = "geo";
        entity.relevance = 0.5;
        entity.tags.add("keyword1");
        final List<NamedEntity> result = Lists.newArrayList();
        result.add(entity);
        return result;
    }

    public List<NamedEntity> namedEntityWithoutKeywords() {
        final NamedEntity entity = new NamedEntity();
        entity.feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        entity.typeReal = RealTopicType.Other;
        final List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        entity.subType = subtypes;
        entity.website = "website";
        entity.geo = "geo";
        entity.relevance = 0.5;
        final List<NamedEntity> result = Lists.newArrayList();
        result.add(entity);
        return result;
    }

    public List<NamedEntity> namedEntitiesWithoutConcepts(final int quantity) {
        final List<NamedEntity> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            final NamedEntity entity = new NamedEntity();
            entity.feelhubLanguage = FeelhubLanguage.fromCountryName("english");
            entity.typeReal = RealTopicType.Other;
            final List<String> subtypes = Lists.newArrayList();
            subtypes.add("subtype1");
            subtypes.add("subtype2");
            subtypes.add("subtype3");
            entity.subType = subtypes;
            entity.website = "website";
            entity.geo = "geo";
            entity.relevance = 0.5;
            entity.tags.add("keyword1");
            result.add(entity);
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

    public AlchemyJsonEntity alchemyJsonEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = new AlchemyJsonEntity();
        alchemyJsonEntity.text = "text";
        alchemyJsonEntity.type = "type";
        alchemyJsonEntity.language = "english";
        alchemyJsonEntity.relevance = 0.5;
        alchemyJsonEntity.disambiguated = new AlchemyJsonDisambiguated();
        alchemyJsonEntity.disambiguated.name = "name";
        alchemyJsonEntity.disambiguated.geo = "geo";
        alchemyJsonEntity.disambiguated.website = "website";
        alchemyJsonEntity.disambiguated.dbpedia = "dbpedia";
        alchemyJsonEntity.disambiguated.yago = "yago";
        alchemyJsonEntity.disambiguated.opencyc = "opencyc";
        alchemyJsonEntity.disambiguated.umbel = "umbel";
        alchemyJsonEntity.disambiguated.freebase = "freebase";
        alchemyJsonEntity.disambiguated.ciaFactbook = "ciaFactbook";
        alchemyJsonEntity.disambiguated.census = "census";
        alchemyJsonEntity.disambiguated.geonames = "geonames";
        alchemyJsonEntity.disambiguated.musicBrainz = "musicBrainz";
        alchemyJsonEntity.disambiguated.crunchbase = "crunchbase";
        alchemyJsonEntity.disambiguated.semanticCrunchbase = "semanticCrunchbase";
        final List<String> subtypes = Lists.newArrayList();
        subtypes.add("subtype1");
        subtypes.add("subtype2");
        subtypes.add("subtype3");
        alchemyJsonEntity.disambiguated.subType = subtypes;
        return alchemyJsonEntity;
    }

    public AlchemyJsonEntity alchemyJsonEntityWithoutDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = new AlchemyJsonEntity();
        alchemyJsonEntity.text = "text";
        alchemyJsonEntity.type = "type";
        alchemyJsonEntity.language = "english";
        alchemyJsonEntity.relevance = 0.5;
        return alchemyJsonEntity;
    }
}
