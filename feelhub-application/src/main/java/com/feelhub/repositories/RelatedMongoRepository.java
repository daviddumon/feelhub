package com.feelhub.repositories;

import com.feelhub.domain.related.*;
import com.google.common.collect.Lists;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class RelatedMongoRepository extends BaseMongoRepository<Related> implements RelatedRepository {

    public RelatedMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Related lookUp(final UUID fromId, final UUID toId) {
        final Criteria criteria = getSession().createCriteria(Related.class);
        criteria.add(Restrictions.equals("fromId", fromId));
        criteria.add(Restrictions.equals("toId", toId));
        return extractOne(criteria);
    }

    @Override
    public List<Related> containingTopicId(final UUID topicId) {
        // There is no need to check for duplicates as long as from-from or to-to is forbidden
        // and obviously, topicId is from in one, an to in the another ^
        final List<Related> relatedList = Lists.newArrayList();
        relatedList.addAll(getListWithFromIdEqualsTopicId(topicId));
        relatedList.addAll(getListWithToIdEqualsTopicId(topicId));
        return relatedList;
    }

    @Override
    public List<Related> forTopicId(final UUID topicId) {
        return getListWithFromIdEqualsTopicId(topicId);
    }

    private List<Related> getListWithFromIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Related.class);
        fromCriteria.add(Restrictions.equals("fromId", topicId));
        return fromCriteria.list();
    }

    private List<Related> getListWithToIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Related.class);
        fromCriteria.add(Restrictions.equals("toId", topicId));
        return fromCriteria.list();
    }
}
