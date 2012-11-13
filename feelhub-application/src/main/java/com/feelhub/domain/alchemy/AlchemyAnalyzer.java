package com.feelhub.domain.alchemy;

import com.feelhub.application.WordService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.relation.AlchemyRelationBinder;
import com.feelhub.repositories.*;
import com.google.common.collect.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.*;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final SessionProvider sessionProvider, final NamedEntityProvider namedEntityProvider, final WordService wordService, final AlchemyRelationBinder alchemyRelationBinder) {
        this.sessionProvider = sessionProvider;
        this.namedEntityProvider = namedEntityProvider;
        this.wordService = wordService;
        this.alchemyRelationBinder = alchemyRelationBinder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final AlchemyRequestEvent event) {
        sessionProvider.start();
        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forTopicId(event.getUri().getTopicId());
        if (alchemyAnalysisList.isEmpty()) {
            addAlchemyAnalysis(event);
        }
        sessionProvider.stop();
    }

    private void addAlchemyAnalysis(final AlchemyRequestEvent event) {
        try {
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(event.getUri());
            final List<AlchemyEntity> entities = createKeywordsAndAlchemyEntities(namedEntities);
            createRelations(event, entities);
        } catch (AlchemyException e) {

        }
    }

    private List<AlchemyEntity> createKeywordsAndAlchemyEntities(final List<NamedEntity> namedEntities) {
        final List<AlchemyEntity> entities = Lists.newArrayList();
        for (final NamedEntity namedEntity : namedEntities) {
            final List<Keyword> keywords = Lists.newArrayList();
            for (final String value : namedEntity.keywords) {
                keywords.add(wordService.lookUpOrCreate(value, namedEntity.feelhubLanguage));
            }
            if (!keywords.isEmpty()) {
                if (keywords.size() > 1) {
                    final KeywordMerger keywordMerger = new KeywordMerger();
                    keywordMerger.merge(keywords);
                }
                final AlchemyEntity alchemyEntity = createAlchemyEntity(namedEntity, keywords.get(0));
                entities.add(alchemyEntity);
            }
        }
        return entities;
    }

    private AlchemyEntity createAlchemyEntity(final NamedEntity namedEntity, final Keyword keyword) {
        final AlchemyEntity alchemyEntity = new AlchemyEntity(keyword.getTopicId());
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

    private void createRelations(final AlchemyRequestEvent event, final List<AlchemyEntity> entities) {
        final HashMap<UUID, Double> topicsAndScores = Maps.newHashMap();
        for (final AlchemyEntity entity : entities) {
            topicsAndScores.put(entity.getTopicId(), entity.getRelevance());
        }
        alchemyRelationBinder.bind(event.getUri().getTopicId(), topicsAndScores);
    }

    private WordService wordService;
    private final AlchemyRelationBinder alchemyRelationBinder;
    private final SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;
}
