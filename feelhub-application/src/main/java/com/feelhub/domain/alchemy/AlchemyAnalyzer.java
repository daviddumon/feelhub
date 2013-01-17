package com.feelhub.domain.alchemy;

import com.feelhub.application.TopicService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.relation.AlchemyRelationBinder;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.*;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final NamedEntityProvider namedEntityProvider, final AlchemyRelationBinder alchemyRelationBinder, final TopicService topicService) {
        this.namedEntityProvider = namedEntityProvider;
        this.alchemyRelationBinder = alchemyRelationBinder;
        this.topicService = topicService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final AlchemyRequestEvent event) {
        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forTopicId(event.getHttpTopic().getId());
        if (alchemyAnalysisList.isEmpty()) {
            addAlchemyAnalysis(event.getHttpTopic());
        }
    }

    private void addAlchemyAnalysis(final HttpTopic httpTopic) {
        try {
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(httpTopic);
            final List<AlchemyEntity> entities = createTopicAndAlchemyEntities(namedEntities, httpTopic.getUserId());
            createRelations(httpTopic, entities);
        } catch (AlchemyException e) {

        }
    }

    private List<AlchemyEntity> createTopicAndAlchemyEntities(final List<NamedEntity> namedEntities, final UUID userId) {
        final List<AlchemyEntity> entities = Lists.newArrayList();
        for (final NamedEntity namedEntity : namedEntities) {
            if (!namedEntity.tags.isEmpty()) {
                entities.add(createTopicAndAlchemyEntity(namedEntity, userId));
            } else {
            }
        }
        return entities;
    }

    private AlchemyEntity createTopicAndAlchemyEntity(final NamedEntity namedEntity, final UUID userId) {
        final RealTopic realTopic = lookUpOrCreateTopic(namedEntity, userId);
        return tryToCreateAlchemyEntity(namedEntity, realTopic);
    }

    private RealTopic lookUpOrCreateTopic(final NamedEntity namedEntity, final UUID userId) {
        final RealTopic realTopic = (RealTopic) topicService.lookUpRealTopic(namedEntity.tags.get(0), namedEntity.type, namedEntity.feelhubLanguage);
        if (realTopic == null) {
            return createTopic(namedEntity, userId);
        }
        return realTopic;
    }

    private RealTopic createTopic(final NamedEntity namedEntity, final UUID userId) {
        final RealTopic realTopic = topicService.createRealTopic(namedEntity.feelhubLanguage, namedEntity.tags.get(0), namedEntity.type);
        realTopic.setUserId(userId);
        for (final String tag : namedEntity.tags) {
            topicService.index(realTopic, tag, namedEntity.feelhubLanguage);
        }
        return realTopic;
    }

    private AlchemyEntity tryToCreateAlchemyEntity(final NamedEntity namedEntity, final RealTopic realTopic) {
        try {
            return existsAlchemyEntity(realTopic.getId());
        } catch (AlchemyEntityNotFound e) {
            return createAlchemyEntity(namedEntity, realTopic);
        }
    }

    private AlchemyEntity existsAlchemyEntity(final UUID topicId) {
        final List<AlchemyEntity> alchemyEntities = Repositories.alchemyEntities().forTopicId(topicId);
        if (alchemyEntities.isEmpty()) {
            throw new AlchemyEntityNotFound();
        }
        return alchemyEntities.get(0);
    }

    private AlchemyEntity createAlchemyEntity(final NamedEntity namedEntity, final RealTopic realTopic) {
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
        Repositories.alchemyEntities().add(alchemyEntity);
        return alchemyEntity;
    }

    private void createRelations(final HttpTopic topic, final List<AlchemyEntity> entities) {
        final HashMap<UUID, Double> topicsAndScores = Maps.newHashMap();
        for (final AlchemyEntity entity : entities) {
            topicsAndScores.put(entity.getTopicId(), entity.getRelevance());
        }
        alchemyRelationBinder.bind(topic.getId(), topicsAndScores);
    }

    private final AlchemyRelationBinder alchemyRelationBinder;
    private TopicService topicService;
    private final NamedEntityProvider namedEntityProvider;
}
