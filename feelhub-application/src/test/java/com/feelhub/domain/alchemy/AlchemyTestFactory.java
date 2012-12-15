package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;

import java.util.*;

public class AlchemyTestFactory {

    public AlchemyEntity newAlchemyEntity(final UUID topicId) {
        final AlchemyEntity alchemyEntity = new AlchemyEntity(topicId);
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
        alchemyEntity.setType(RealTopicType.Automobile);
        alchemyEntity.setUmbel("umbel");
        alchemyEntity.setWebsite("website");
        alchemyEntity.setYago("yago");
        alchemyEntity.setRelevance(1.0);
        Repositories.alchemyEntities().add(alchemyEntity);
        return alchemyEntity;
    }

    public AlchemyAnalysis newAlchemyAnalysis(final HttpTopic httpTopic) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(httpTopic);
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }

    public AlchemyAnalysis newAlchemyAnalysis() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(httpTopic);
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }
}
