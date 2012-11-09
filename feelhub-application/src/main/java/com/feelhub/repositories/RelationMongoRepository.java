package com.feelhub.repositories;

import com.feelhub.domain.relation.*;
import com.google.common.collect.Lists;
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
        return extractOne(criteria);
    }

    @Override
    public List<Relation> forTopicId(final UUID topicId) {
        final CopyOnWriteArrayList<Relation> relations = Lists.newCopyOnWriteArrayList();
        final List<Relation> listWithFromIdEqualsTopicId = getListWithFromIdEqualsTopicId(topicId);
        if (!listWithFromIdEqualsTopicId.isEmpty()) {
            relations.addAllAbsent(listWithFromIdEqualsTopicId);
        }
        final List<Relation> listWithToIdEqualsTopicId = getListWithToIdEqualsTopicId(topicId);
        if (!listWithToIdEqualsTopicId.isEmpty()) {
            relations.addAllAbsent(listWithToIdEqualsTopicId);
        }
        return relations;
    }

    private List<Relation> getListWithFromIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Relation.class);
        fromCriteria.add(Restrictions.equals("fromId", topicId));
        return fromCriteria.list();
    }

    private List<Relation> getListWithToIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Relation.class);
        fromCriteria.add(Restrictions.equals("toId", topicId));
        return fromCriteria.list();
    }
}
