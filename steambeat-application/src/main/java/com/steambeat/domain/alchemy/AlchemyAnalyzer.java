package com.steambeat.domain.alchemy;

import com.google.common.collect.*;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.relation.AlchemyRelationBinder;
import com.steambeat.repositories.*;

import java.util.*;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final SessionProvider sessionProvider, final NamedEntityProvider namedEntityProvider, final KeywordService keywordService, final AlchemyRelationBinder alchemyRelationBinder) {
        this.sessionProvider = sessionProvider;
        this.namedEntityProvider = namedEntityProvider;
        this.keywordService = keywordService;
        this.alchemyRelationBinder = alchemyRelationBinder;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final AlchemyRequestEvent event) {
        sessionProvider.start();
        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().forReferenceId(event.getUri().getReferenceId());
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
        List<AlchemyEntity> entities = Lists.newArrayList();
        for (final NamedEntity namedEntity : namedEntities) {
            List<Keyword> keywords = Lists.newArrayList();
            for (final String value : namedEntity.keywords) {
                keywords.add(keywordService.lookUpOrCreate(value, namedEntity.steambeatLanguage.getCode()));
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
        final AlchemyEntity alchemyEntity = new AlchemyEntity(keyword.getReferenceId());
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
        HashMap<UUID, Double> referencesAndScores = Maps.newHashMap();
        for (AlchemyEntity entity : entities) {
            referencesAndScores.put(entity.getReferenceId(), entity.getRelevance());
        }
        alchemyRelationBinder.bind(event.getUri().getReferenceId(), referencesAndScores);
    }

    private final KeywordService keywordService;
    private AlchemyRelationBinder alchemyRelationBinder;
    private final SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;
}
