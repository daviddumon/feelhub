package com.steambeat.domain.textAnalytics;

import com.google.inject.Inject;
import com.steambeat.domain.subject.*;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class NamedEntityAnalyzer {

    @Inject
    public NamedEntityAnalyzer(final NamedEntityProvider provider) {
        this.provider = provider;
    }

    public void analyze(final WebPage webpage) {
        final List<NamedEntity> entities = provider.entitiesFor(webpage);
        for (final NamedEntity entity : entities) {
            link(webpage, createConcept(entity));
        }
    }

    private Concept createConcept(final NamedEntity entity) {
        final Concept concept = new ConceptFactory().newConcept(entity);
        Repositories.concepts().add(concept);
        return concept;
    }

    private void link(final WebPage webpage, final Concept concept) {
        final Relation relation1 = getRelationFactory().newRelation(webpage, concept);
        final Relation relation2 = getRelationFactory().newRelation(concept, webpage);
        Repositories.relations().add(relation1);
        Repositories.relations().add(relation2);
    }

    private RelationFactory getRelationFactory() {
        return new RelationFactory();
    }

    private final NamedEntityProvider provider;
}
