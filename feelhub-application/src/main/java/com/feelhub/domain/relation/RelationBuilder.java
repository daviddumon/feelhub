package com.feelhub.domain.relation;

import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

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
        if (!left.equals(right)) {
            final Relation relation = Repositories.relations().lookUp(left.getId(), right.getId());
            if (relation == null) {
                createNewRelation(left, right, additionalWeight);
            } else {
                relation.addWeight(1.0 + additionalWeight);
            }
        }
    }

    private void createNewRelation(final Reference left, final Reference right, final double additionalWeight) {
        final Relation relation = relationFactory.newRelation(left, right, additionalWeight);
        Repositories.relations().add(relation);
    }

    private final RelationFactory relationFactory;
}
