package com.steambeat.domain.relation;

import com.google.inject.Inject;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

public class RelationBuilder {

    @Inject
    public RelationBuilder(final RelationFactory relationFactory) {
        this.relationFactory = relationFactory;
    }

    public void connectTwoWays(final Reference from, final Reference to) {
        connectTwoWays(from, to, 0.0);
    }

    public void connectTwoWays(final Reference from, final Reference to, final double additionalWeight) {
        connectOneWay(from, to, additionalWeight);
        connectOneWay(to, from, additionalWeight);
    }

    private void connectOneWay(final Reference left, final Reference right, final double additionalWeight) {
        final Relation relation = Repositories.relations().lookUp(left.getId(), right.getId());
        if (relation == null) {
            createNewRelation(left, right, additionalWeight);
        } else {
            relation.addWeight(1.0 + additionalWeight);
        }
    }

    private void createNewRelation(final Reference left, final Reference right, final double additionalWeight) {
        final Relation relation = relationFactory.newRelation(left, right, additionalWeight);
        Repositories.relations().add(relation);
    }

    private final RelationFactory relationFactory;
}
