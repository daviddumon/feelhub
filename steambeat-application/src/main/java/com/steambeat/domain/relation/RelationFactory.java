package com.steambeat.domain.relation;

import com.steambeat.domain.subject.Subject;

public class RelationFactory {

    public Relation newRelation(final Subject left, final Subject right) {
        return new Relation(left, right);
    }
}
