package com.feelhub.repositories;

import com.feelhub.domain.admin.AlchemyStatistic;
import com.feelhub.domain.admin.AlchemyStatisticsRepository;
import com.feelhub.domain.alchemy.AlchemyEntity;
import com.feelhub.domain.alchemy.AlchemyEntityRepository;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;
import java.util.UUID;

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
