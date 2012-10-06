package com.steambeat.repositories;

import com.steambeat.domain.statistics.*;
import org.joda.time.Interval;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;


public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        return criteria.list();
    }

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId, final Granularity granularity) {
        final Criteria criteria = criteriaForReferenceAndGranularity(referenceId, granularity);
        return criteria.list();
    }

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForReferenceAndGranularity(referenceId, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return criteria.list();
    }

    private Criteria criteriaForReferenceAndGranularity(final UUID referenceID, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("referenceId", referenceID));
        criteria.add(Restrictions.equals("granularity", granularity));
        return criteria;
    }
}
