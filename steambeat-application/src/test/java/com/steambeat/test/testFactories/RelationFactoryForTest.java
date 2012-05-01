package com.steambeat.test.testFactories;

import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.repositories.Repositories;

public class RelationFactoryForTest {

    public Relation newRelation(final Subject from, final Subject to) {
        return newRelation(from, to, 1);
    }

    public Relation newRelation(final Subject from, final Subject to, final int weight) {
        final Relation relation = new Relation(from, to);
        relation.setWeight(weight);
        Repositories.relations().add(relation);
        return relation;
    }

    public void newRelations(final int quantity) {
        newRelations(quantity, TestFactories.subjects().newWebPage());
    }

    public void newRelations(final int quantity, final Subject from) {
        for (int i = 0; i < quantity; i++) {
            createARelation(from, i);
        }
    }

    private void createARelation(final Subject from, final int weight) {
        final Concept to = TestFactories.subjects().newConcept();
        newRelation(from, to, weight);
    }
}
