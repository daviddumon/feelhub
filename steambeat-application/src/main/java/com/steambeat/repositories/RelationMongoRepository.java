package com.steambeat.repositories;

import com.google.common.collect.Lists;
import com.steambeat.domain.relation.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RelationMongoRepository extends BaseMongoRepository<Relation> implements RelationRepository {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Relation lookUp(final UUID fromId, final UUID toId) {
        final Criteria criteria = getSession().createCriteria(Relation.class);
        criteria.add(Restrictions.equals("fromId", fromId));
        criteria.add(Restrictions.equals("toId", toId));
        final List<Relation> relations = criteria.list();
        if (relations.isEmpty()) {
            return null;
        } else {
            return relations.get(0);
        }
    }

    @Override
    public List<Relation> forReferenceId(final UUID referenceId) {
        CopyOnWriteArrayList<Relation> relations = Lists.newCopyOnWriteArrayList();
        final List<Relation> listWithFromIdEqualsReferenceId = getListWithFromIdEqualsReferenceId(referenceId);
        if (!listWithFromIdEqualsReferenceId.isEmpty()) {
            relations.addAllAbsent(listWithFromIdEqualsReferenceId);
        }
        final List<Relation> listWithToIdEqualsReferenceId = getListWithToIdEqualsReferenceId(referenceId);
        if (!listWithToIdEqualsReferenceId.isEmpty()) {
            relations.addAllAbsent(listWithToIdEqualsReferenceId);
        }
        return relations;
    }

    private List<Relation> getListWithFromIdEqualsReferenceId(final UUID referenceId) {
        final Criteria fromCriteria = getSession().createCriteria(Relation.class);
        fromCriteria.add(Restrictions.equals("fromId", referenceId));
        return fromCriteria.list();
    }

    private List<Relation> getListWithToIdEqualsReferenceId(final UUID referenceId) {
        final Criteria fromCriteria = getSession().createCriteria(Relation.class);
        fromCriteria.add(Restrictions.equals("toId", referenceId));
        return fromCriteria.list();
    }
}
