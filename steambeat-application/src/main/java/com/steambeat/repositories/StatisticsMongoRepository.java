package com.steambeat.repositories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.reference.Reference;
import org.joda.time.Interval;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;


public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Statistics> forReference(final Reference reference, final Granularity granularity) {
        final Criteria criteria = criteriaForReferenceAndGranularity(reference, granularity);
        return (List<Statistics>) criteria.list();
    }

    @Override
    public List<Statistics> forReference(final Reference reference, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForReferenceAndGranularity(reference, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return (List<Statistics>) criteria.list();
    }

    private Criteria criteriaForReferenceAndGranularity(final Reference reference, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("referenceId", reference.getId()));
        criteria.add(Restrictions.equals("granularity", granularity));
        return criteria;
    }
}
