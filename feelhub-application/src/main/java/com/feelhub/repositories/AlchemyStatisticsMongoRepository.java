package com.feelhub.repositories;

import com.feelhub.domain.admin.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

public class AlchemyStatisticsMongoRepository extends BaseMongoRepository<AlchemyStatistic> implements AlchemyStatisticsRepository {

    public AlchemyStatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public AlchemyStatistic byMonth(String month) {
        final Criteria criteria = getSession().createCriteria(AlchemyStatistic.class);
        criteria.add(Restrictions.equals("month", month));
        return extractOne(criteria);
    }
}
