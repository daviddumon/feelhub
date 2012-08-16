package com.steambeat.domain.relation;

import com.steambeat.domain.reference.Reference;

public class RelationFactory {

    public Relation newRelation(final Reference left, final Reference right) {
        return new Relation(left, right, 1.0);
    }

    public Relation newRelation(final Reference left, final Reference right, final double additionalWeight) {
        return new Relation(left, right, 1.0 + additionalWeight);
    }
}
