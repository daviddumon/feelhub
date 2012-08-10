package com.steambeat.domain.relation;

import com.steambeat.domain.topic.Topic;

public class RelationFactory {

    public Relation newRelation(final Topic left, final Topic right) {
        return new Relation(left, right, 1.0);
    }

    public Relation newRelation(final Topic left, final Topic right, final double additionalWeight) {
        return new Relation(left, right, 1.0 + additionalWeight);
    }
}
