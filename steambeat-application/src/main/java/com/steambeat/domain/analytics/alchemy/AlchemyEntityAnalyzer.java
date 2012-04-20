package com.steambeat.domain.analytics.alchemy;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.*;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final AlchemyEntityProvider provider) {
        this.provider = provider;
    }

    public void analyze(final WebPage webpage) {
        final List<AlchemyJsonEntity> results = provider.entitiesFor(webpage);
        for (final AlchemyJsonEntity alchemyJsonEntity : results) {
            link(webpage, createConcept(alchemyJsonEntity));
        }
    }

    private Concept createConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final Concept concept = new ConceptFactory().newConcept(alchemyJsonEntity);
        Repositories.subjects().add(concept);
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

    private final AlchemyEntityProvider provider;
}
