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
            postEvent(conceptEvents);
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
        List<ConceptEvent> events = Lists.newArrayList();
        for (NamedEntity namedEntity : namedEntities) {
            final ConceptEvent conceptEvent = new ConceptEvent();
            addKeywordsToConceptEvent(namedEntity, conceptEvent);
            createAlchemy(namedEntity, conceptEvent);
            events.add(conceptEvent);
        }
        return events;
    }

    private void addKeywordsToConceptEvent(final NamedEntity namedEntity, final ConceptEvent conceptEvent) {
        for (String value : namedEntity.keywords) {
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
        if (conceptEvent.getKeywords().get(0) != null) {
            final Alchemy alchemy = new Alchemy();
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
            alchemy.setReferenceId(conceptEvent.getKeywords().get(0).getReferenceId());
            alchemy.setRelevance(namedEntity.relevance);
            Repositories.alchemys().add(alchemy);
        }
    }

    private void postEvent(final List<ConceptEvent> conceptEvents) {
        final ConceptGroupEvent event = new ConceptGroupEvent();
        event.addAllAbsent(conceptEvents);
        DomainEventBus.INSTANCE.post(event);
    }

    private final KeywordService keywordService;
    private SessionProvider sessionProvider;
    private final NamedEntityProvider namedEntityProvider;


    //public void analyze(final String uri) {
    //    final List<NamedEntity> namedEntities = namedEntityProvider.entitiesFor(uri);
    //    for (final NamedEntity namedEntity : namedEntities) {
    //        if (!namedEntity.keywords.isEmpty()) {
    //            handle(uri, namedEntity);
    //        }
    //    }
    //}

    //
    //private void handle(final String uri, final NamedEntity namedEntity) {
    //    createConcept(namedEntity);
    //    createAssociations(namedEntity);
    //    createRelations(uri, namedEntity);
    //}
    //
    //private void createConcept(final NamedEntity namedEntity) {
    //    if (namedEntity.conceptId == null) {
    //        namedEntity.conceptId = UUID.randomUUID();
    //        //final Concept concept = conceptFactory.newConcept(namedEntity);
    //        //Repositories.subjects().add(concept);
    //        //concepts.add(new Concept());
    //    } else {
    //        //concepts.add(new Concept(namedEntity.conceptId));
    //    }
    //}
    //
    //private void createAssociations(final NamedEntity namedEntity) {
    //    for (final String keyword : namedEntity.keywords) {
    //        try {
    //            associationService.lookUp(keyword, namedEntity.steambeatLanguage);
    //        } catch (KeywordNotFound e) {
    //            //associationService.createAssociationFor(new Tag(keyword), namedEntity.conceptId, namedEntity.language);
    //        }
    //    }
    //}
    //
    //private void createRelations(final Uri uri, final NamedEntity namedEntity) {
    //    //final Concept concept = getLastAddedConcept();
    //    //relationBuilder.connectTwoWays(uri, concept, namedEntity.relevance);
    //    //for (final Concept otherConcept : concepts) {
    //    //    if (!concept.equals(otherConcept)) {
    //    //relationBuilder.connectTwoWays(concept, otherConcept);
    //    //}
    //    //}
    //}
    //
    ////private Concept getLastAddedConcept() {
    ////    return concepts.get(concepts.size() - 1);
    ////}
    //
    //private final RelationBuilder relationBuilder = new RelationBuilder(new RelationFactory());
    ////private final List<Concept> concepts = Lists.newArrayList();
}
