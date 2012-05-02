package com.steambeat.domain.relation;

import com.google.inject.Inject;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

public class Linker {

    @Inject
    public Linker(final RelationFactory relationFactory) {
        this.relationFactory = relationFactory;
    }

    public void connectTwoWays(final WebPage from, final Concept to) {
        connectOneWay(from, to);
        connectOneWay(to, from);
    }

    private void connectOneWay(final Subject left, final Subject right) {
        Relation relation = lookUpRelation(left, right);
        if (relation == null) {
            createNewRelation(left, right);
        } else {

        }
    }

    private Relation lookUpRelation(final Subject left, final Subject right) {
        return null;
    }

    private void createNewRelation(final Subject left, final Subject right) {
        final Relation relation = relationFactory.newRelation(left, right);
        Repositories.relations().add(relation);
    }

    private RelationFactory relationFactory;
}
