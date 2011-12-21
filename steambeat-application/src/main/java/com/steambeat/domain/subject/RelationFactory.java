package com.steambeat.domain.subject;

public class RelationFactory {
    public Relation newRelation(Subject left, Subject right) {
        return new Relation(left, right);
    }
}
