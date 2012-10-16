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
        final Keyword keyword;
        try {
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(event.getUri().getValue());
            createKeywords(namedEntities);

            // Relier les references de tous les keywords
        } catch (AlchemyException e) {

        }
        sessionProvider.stop();
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
                createAlchemy(namedEntity, keywords.get(0));
            }
        }
    }

    private void createAlchemy(final NamedEntity namedEntity, final Keyword keyword) {
        final AlchemyEntity alchemy = new AlchemyEntity(keyword.getReferenceId());
        alchemy.setCensus(namedEntity.census);
        alchemy.setCiafactbook(namedEntity.ciaFactbook);
        alchemy.setCrunchbase(namedEntity.crunchbase);
        alchemy.setDbpedia(namedEntity.dbpedia);
        alchemy.setFreebase(namedEntity.freebase);
        alchemy.setGeo(namedEntity.geo);
        alchemy.setGeonames(namedEntity.geonames);
        alchemy.setMusicbrainz(namedEntity.musicBrainz);
        alchemy.setOpencyc(namedEntity.opencyc);
        alchemy.setSemanticcrunchbase(namedEntity.semanticCrunchbase);
        alchemy.setSubtype(namedEntity.subType);
        alchemy.setType(namedEntity.type);
        alchemy.setUmbel(namedEntity.umbel);
        alchemy.setWebsite(namedEntity.website);
        alchemy.setYago(namedEntity.yago);
        alchemy.setRelevance(namedEntity.relevance);
        Repositories.alchemyEntities().add(alchemy);
    }

    private final KeywordService keywordService;
    private final SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;
}
