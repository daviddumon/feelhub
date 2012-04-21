package com.steambeat.domain.analytics.alchemy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.analytics.*;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.Subject;
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
        createConcepts(results);
        createRelations(webpage);
    }

    private void createConcepts(final List<AlchemyJsonEntity> results) {
        for (final AlchemyJsonEntity alchemyJsonEntity : results) {
            concepts.add(createConcept(alchemyJsonEntity));
        }
    }

    private Concept createConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final Concept concept = new ConceptFactory().newConcept(alchemyJsonEntity);
        Repositories.subjects().add(concept);
        return concept;
    }

    private void createRelations(final WebPage webpage) {
        for (Concept concept : concepts) {
            link(webpage, concept);
            for(int i = concepts.lastIndexOf(concept); i < concepts.size(); i++) {
                Concept otherConcept = concepts.get(i);
                if (!concept.equals(otherConcept)) {
                    link(concept, otherConcept);
                }
            }
        }
    }

    private void link(final Subject left, final Subject right) {
        final Relation relation1 = getRelationFactory().newRelation(left, right);
        final Relation relation2 = getRelationFactory().newRelation(right, left);
        Repositories.relations().add(relation1);
        Repositories.relations().add(relation2);
    }

    private RelationFactory getRelationFactory() {
        return new RelationFactory();
    }

    private final AlchemyEntityProvider provider;
    private List<Concept> concepts = Lists.newArrayList();
}
