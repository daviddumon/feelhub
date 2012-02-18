package com.steambeat.domain.subject;

public class RelationFactory {

    public Relation newRelation(final Subject left, final Subject right) {
        return new Relation(left, right);
    }
}
