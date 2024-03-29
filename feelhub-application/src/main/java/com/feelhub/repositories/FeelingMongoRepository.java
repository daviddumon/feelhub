package com.feelhub.repositories;

import com.feelhub.domain.feeling.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class FeelingMongoRepository extends BaseMongoRepository<Feeling> implements FeelingRepository {

    public FeelingMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Feeling> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(Feeling.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }

    @Override
    public List<Feeling> forTopicIdUserIdAndFeelingValue(final UUID topicId, final UUID userId, final FeelingValue feelingValue) {
        final Criteria criteria = getSession().createCriteria(Feeling.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        criteria.add(Restrictions.equals("userId", userId));
        criteria.add(Restrictions.equals("feelingValue", feelingValue));
        return criteria.list();
    }
}
