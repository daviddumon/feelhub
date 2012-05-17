package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final NamedEntityProvider NamedEntityProvider, final AssociationService associationService) {
        this.NamedEntityProvider = NamedEntityProvider;
        this.associationService = associationService;
    }

    public void analyze(final WebPage webpage) {
        List<NamedEntity> namedEntities = NamedEntityProvider.entitiesFor(webpage);
        for (NamedEntity namedEntity : namedEntities) {
            if (!namedEntity.keywords.isEmpty()) {
                handle(webpage, namedEntity);
            }
        }
    }

    private void handle(final WebPage webpage, final NamedEntity namedEntity) {
        createConcept(namedEntity);
        createAssociations(namedEntity);
        createRelations(webpage, namedEntity);
    }

    private void createConcept(final NamedEntity namedEntity) {
        if (namedEntity.conceptId == null) {
            namedEntity.conceptId = UUID.randomUUID();
            final Concept concept = new ConceptFactory().newConcept(namedEntity);
            Repositories.subjects().add(concept);
            concepts.add(concept);
        } else {
            concepts.add(new Concept(namedEntity.conceptId));
        }
    }

    private void createAssociations(final NamedEntity namedEntity) {
        for (String keyword : namedEntity.keywords) {
            try {
                associationService.lookUp(new Tag(keyword), namedEntity.language);
            } catch (AssociationNotFound e) {
                associationService.createAssociationFor(new Tag(keyword), namedEntity.conceptId, namedEntity.language);
            }
        }
    }

    private void createRelations(final WebPage webpage, final NamedEntity namedEntity) {
        final Concept concept = getLastAddedConcept();
        relationBuilder.connectTwoWays(webpage, concept, namedEntity.relevance);
        for (Concept otherConcept : concepts) {
            if (!concept.equals(otherConcept)) {
                relationBuilder.connectTwoWays(concept, otherConcept);
            }
        }
    }

    private Concept getLastAddedConcept() {
        return concepts.get(concepts.size() - 1);
    }

    private final NamedEntityProvider NamedEntityProvider;
    private AssociationService associationService;
    private final RelationBuilder relationBuilder = new RelationBuilder(new RelationFactory());
    private List<Concept> concepts = Lists.newArrayList();
}
