package com.feelhub.repositories;

import com.feelhub.domain.relation.*;
import com.google.common.collect.Lists;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

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
    public List<Relation> containingTopicId(final UUID topicId) {
        // There is no need to check for duplicates as long as from-from or to-to is forbidden
        // and obviously, topicId is from in one, an to in the another ^
        final List<Relation> relations = Lists.newArrayList();
        relations.addAll(getListWithFromIdEqualsTopicId(topicId));
        relations.addAll(getListWithToIdEqualsTopicId(topicId));
        return relations;
    }

    @Override
    public List<Related> relatedForTopicId(final UUID topicId) {
        return getRelatedListWithFromIdEqualsTopicId(topicId);
    }

    private List<Related> getRelatedListWithFromIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Relation.class);
        fromCriteria.add(Restrictions.equals("__discriminator", "Related"));
        fromCriteria.add(Restrictions.equals("fromId", topicId));
        return fromCriteria.list();
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
