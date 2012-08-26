package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.UriReferencesChangedEvent;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.*;

import java.util.*;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final SessionProvider sessionProvider, final NamedEntityProvider namedEntityProvider, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.namedEntityProvider = namedEntityProvider;
        this.keywordService = keywordService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriReferencesChangedEvent event) {
        sessionProvider.start();
        final Keyword keyword;
        try {
            keyword = getFirstKeywordFor(event);
            final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(keyword.getValue());
            final List<ConceptEvent> conceptEvents = createConceptEvents(namedEntities);
            postEvent(conceptEvents, event.getNewReferenceId());
        } catch (AlchemyException e) {

        }
        sessionProvider.stop();
    }

    private Keyword getFirstKeywordFor(final UriReferencesChangedEvent event) {
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(event.getNewReferenceId());
        if (!keywords.isEmpty()) {
            return keywords.get(0);
        } else {
            throw new AlchemyException();
        }
    }

    private List<ConceptEvent> createConceptEvents(final List<NamedEntity> namedEntities) {
        final List<ConceptEvent> events = Lists.newArrayList();
        for (final NamedEntity namedEntity : namedEntities) {
            final ConceptEvent conceptEvent = new ConceptEvent();
            addKeywordsToConceptEvent(namedEntity, conceptEvent);
            createAlchemy(namedEntity, conceptEvent);
            events.add(conceptEvent);
        }
        return events;
    }

    private void addKeywordsToConceptEvent(final NamedEntity namedEntity, final ConceptEvent conceptEvent) {
        for (final String value : namedEntity.keywords) {
            final Keyword keyword = getOrCreateKeyword(value, namedEntity.steambeatLanguage);
            conceptEvent.addIfAbsent(keyword);
        }
    }

    private Keyword getOrCreateKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        Keyword keyword;
        try {
            keyword = keywordService.lookUp(value, steambeatLanguage);
        } catch (KeywordNotFound e) {
            keyword = keywordService.createKeywordWithoutEvent(value, steambeatLanguage);
        }
        return keyword;
    }

    private void createAlchemy(final NamedEntity namedEntity, final ConceptEvent conceptEvent) {
        final Keyword keyword = conceptEvent.getKeywords().get(0);
        if (keyword != null) {
            final Alchemy alchemy = new Alchemy(keyword.getReferenceId());
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
            Repositories.alchemys().add(alchemy);
        }
    }

    private void postEvent(final List<ConceptEvent> conceptEvents, final UUID newReferenceId) {
        final ConceptGroupEvent event = new ConceptGroupEvent(newReferenceId);
        event.addAllAbsent(conceptEvents);
        DomainEventBus.INSTANCE.post(event);
    }

    private final KeywordService keywordService;
    private final SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;
}
