package com.feelhub.domain.alchemy;

import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;

import java.util.*;

public class AlchemyTestFactory {

    public AlchemyEntity newAlchemyEntityEntity(final UUID topicId) {
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
        alchemyEntity.setTypeReal(RealTopicType.Automobile);
        alchemyEntity.setUmbel("umbel");
        alchemyEntity.setWebsite("website");
        alchemyEntity.setYago("yago");
        alchemyEntity.setRelevance(1.0);
        Repositories.alchemyEntities().add(alchemyEntity);
        return alchemyEntity;
    }

    public AlchemyAnalysis newAlchemyAnalysis(final RealTopic realTopic) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(realTopic, "http://www.fakeurl.com");
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }

    public AlchemyAnalysis newAlchemyAnalysis() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(realTopic, "http://www.fakeurl.com");
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
        return alchemyAnalysis;
    }
}
