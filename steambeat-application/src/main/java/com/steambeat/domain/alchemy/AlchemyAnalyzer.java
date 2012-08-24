package com.steambeat.domain.alchemy;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.uri.UriEvent;

public class AlchemyAnalyzer {

    @Inject
    public AlchemyAnalyzer(final NamedEntityProvider namedEntityProvider, final KeywordService associationService) {
        //this.NamedEntityProvider = NamedEntityProvider;
        //this.associationService = associationService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriEvent event) {
        //analyze(event.getUri());
    }

    public void analyze(final String uri) {
        //    final List<NamedEntity> namedEntities = NamedEntityProvider.entitiesFor(uri);
        //    for (final NamedEntity namedEntity : namedEntities) {
        //        if (!namedEntity.keywords.isEmpty()) {
        //            handle(uri, namedEntity);
        //        }
        //    }
    }
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
    //private final NamedEntityProvider NamedEntityProvider;
    //private final KeywordService associationService;
    //private final RelationBuilder relationBuilder = new RelationBuilder(new RelationFactory());
    ////private final List<Concept> concepts = Lists.newArrayList();
}
