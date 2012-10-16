package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.repositories.*;

import java.util.List;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final SessionProvider sessionProvider, final NamedEntityProvider namedEntityProvider, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.namedEntityProvider = namedEntityProvider;
        this.keywordService = keywordService;
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
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(event.getUri().getValue());
            createKeywords(namedEntities);
            createAlchemyAnalysis(event.getUri());
            // Relier les references de tous les keywords
        } catch (AlchemyException e) {

        }
    }

    private void createKeywords(final List<NamedEntity> namedEntities) {
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
                createAlchemyEntity(namedEntity, keywords.get(0));
            }
        }
    }

    private void createAlchemyEntity(final NamedEntity namedEntity, final Keyword keyword) {
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
    }

    private void createAlchemyAnalysis(final Keyword uri) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(uri);
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
    }

    private final KeywordService keywordService;
    private final SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;
}
