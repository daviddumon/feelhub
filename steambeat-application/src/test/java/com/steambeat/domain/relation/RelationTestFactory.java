package com.steambeat.domain.relation;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;

public class RelationTestFactory {

    public Relation newRelation() {
        return newRelation(TestFactories.references().newReference(), TestFactories.references().newReference());
    }

    public Relation newRelation(final Reference from, final Reference to) {
        return newRelation(from, to, 1);
    }

    public Relation newRelation(final Reference from, final Reference to, final int weight) {
        final Relation relation = new Relation(from, to, 1.0);
        relation.setWeight(weight);
        Repositories.relations().add(relation);
        return relation;
    }

    public void newRelations(final int quantity) {
        newRelations(quantity, TestFactories.references().newReference());
    }

    public void newRelations(final int quantity, final Reference from) {
        for (int i = 0; i < quantity; i++) {
            createARelation(from, i);
        }
    }

    private void createARelation(final Reference from, final int weight) {
        final Reference to = TestFactories.references().newReference();
        newRelation(from, to, weight);
    }
}
