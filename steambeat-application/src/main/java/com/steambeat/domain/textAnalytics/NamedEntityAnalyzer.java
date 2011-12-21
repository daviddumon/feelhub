package com.steambeat.domain.textAnalytics;

import com.google.inject.Inject;
import com.steambeat.domain.subject.Relation;
import com.steambeat.domain.subject.RelationFactory;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.concept.ConceptFactory;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class NamedEntityAnalyzer {

    @Inject
    public NamedEntityAnalyzer(NamedEntityProvider provider) {
        this.provider = provider;
    }

    public void analyze(WebPage webpage) {
        List<NamedEntity> entities = provider.entitiesFor(webpage);
        for (NamedEntity entity : entities) {
            link(webpage, createConcept(entity));
        }
    }

    private Concept createConcept(NamedEntity entity) {
        final Concept concept = new ConceptFactory().newConcept(entity);
        Repositories.concepts().add(concept);
        return concept;
    }

    private void link(WebPage webpage, Concept concept) {
        Relation relation1 = getRelationFactory().newRelation(webpage, concept);
        Relation relation2 = getRelationFactory().newRelation(concept, webpage);
        Repositories.relations().add(relation1);
        Repositories.relations().add(relation2);
    }

    private RelationFactory getRelationFactory() {
        return new RelationFactory();
    }

    private NamedEntityProvider provider;
}
