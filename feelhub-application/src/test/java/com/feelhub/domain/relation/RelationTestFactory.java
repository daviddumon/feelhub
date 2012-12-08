package com.feelhub.domain.relation;

import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

import java.util.UUID;

public class RelationTestFactory {

    public Relation newRelation() {
        return newRelation(TestFactories.topics().newCompleteRealTopic().getId(), TestFactories.topics().newCompleteRealTopic().getId());
    }

    public Relation newRelation(final UUID fromId, final UUID toId) {
        return newRelation(fromId, toId, 1);
    }

    public Relation newRelation(final UUID fromId, final UUID toId, final int weight) {
        final Relation relation = new Relation(fromId, toId, 1.0);
        relation.setWeight(weight);
        Repositories.relations().add(relation);
        return relation;
    }

    public void newRelations(final int quantity) {
        newRelations(quantity, TestFactories.topics().newCompleteRealTopic().getId());
    }

    public void newRelations(final int quantity, final UUID fromid) {
        for (int i = 0; i < quantity; i++) {
            createARelation(fromid, i);
        }
    }

    private void createARelation(final UUID fromId, final int weight) {
        newRelation(fromId, TestFactories.topics().newCompleteRealTopic().getId(), weight);
    }
}
