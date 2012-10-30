package com.feelhub.repositories;

import com.feelhub.domain.opinion.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class OpinionMongoRepository extends BaseMongoRepository<Opinion> implements OpinionRepository {

    public OpinionMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Opinion> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(Opinion.class);
        criteria.add(Restrictions.equals("judgments.referenceId", referenceId));
        return criteria.list();
    }
}
