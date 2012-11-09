package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

public class RelationTestFactory {

    public Relation newRelation() {
        return newRelation(TestFactories.topics().newTopic(), TestFactories.topics().newTopic());
    }

    public Relation newRelation(final Topic from, final Topic to) {
        return newRelation(from, to, 1);
    }

    public Relation newRelation(final Topic from, final Topic to, final int weight) {
        final Relation relation = new Relation(from, to, 1.0);
        relation.setWeight(weight);
        Repositories.relations().add(relation);
        return relation;
    }

    public void newRelations(final int quantity) {
        newRelations(quantity, TestFactories.topics().newTopic());
    }

    public void newRelations(final int quantity, final Topic from) {
        for (int i = 0; i < quantity; i++) {
            createARelation(from, i);
        }
    }

    private void createARelation(final Topic from, final int weight) {
        final Topic to = TestFactories.topics().newTopic();
        newRelation(from, to, weight);
    }
}
