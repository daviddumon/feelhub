package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.subject.concept.*;

import java.util.*;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final NamedEntityProvider NamedEntityProvider, final AssociationService associationService, final ConceptFactory conceptFactory) {
        this.NamedEntityProvider = NamedEntityProvider;
        this.associationService = associationService;
        this.conceptFactory = conceptFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final UriCreatedEvent event) {
        analyze(event.getUri());
    }

    public void analyze(final Uri uri) {
        final List<NamedEntity> namedEntities = NamedEntityProvider.entitiesFor(uri);
        for (final NamedEntity namedEntity : namedEntities) {
            if (!namedEntity.keywords.isEmpty()) {
                handle(uri, namedEntity);
            }
        }
    }

    private void handle(final Uri uri, final NamedEntity namedEntity) {
        createConcept(namedEntity);
        createAssociations(namedEntity);
        createRelations(uri, namedEntity);
    }

    private void createConcept(final NamedEntity namedEntity) {
        if (namedEntity.conceptId == null) {
            namedEntity.conceptId = UUID.randomUUID();
            final Concept concept = conceptFactory.newConcept(namedEntity);
            //Repositories.subjects().add(concept);
            concepts.add(concept);
        } else {
            concepts.add(new Concept(namedEntity.conceptId));
        }
    }

    private void createAssociations(final NamedEntity namedEntity) {
        for (final String keyword : namedEntity.keywords) {
            try {
                associationService.lookUp(new Tag(keyword), namedEntity.language);
            } catch (AssociationNotFound e) {
                associationService.createAssociationFor(new Tag(keyword), namedEntity.conceptId, namedEntity.language);
            }
        }
    }

    private void createRelations(final Uri uri, final NamedEntity namedEntity) {
        final Concept concept = getLastAddedConcept();
        //relationBuilder.connectTwoWays(uri, concept, namedEntity.relevance);
        for (final Concept otherConcept : concepts) {
            if (!concept.equals(otherConcept)) {
                //relationBuilder.connectTwoWays(concept, otherConcept);
            }
        }
    }

    private Concept getLastAddedConcept() {
        return concepts.get(concepts.size() - 1);
    }

    private final NamedEntityProvider NamedEntityProvider;
    private final AssociationService associationService;
    private final ConceptFactory conceptFactory;
    private final RelationBuilder relationBuilder = new RelationBuilder(new RelationFactory());
    private final List<Concept> concepts = Lists.newArrayList();
}
