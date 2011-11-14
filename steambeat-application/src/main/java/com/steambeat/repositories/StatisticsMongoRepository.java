package com.steambeat.repositories;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.statistics.*;
import fr.bodysplash.mongolink.MongoSession;
import fr.bodysplash.mongolink.domain.criteria.*;
import org.joda.time.Interval;

import java.util.List;

@SuppressWarnings("unchecked")
public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Statistics> forFeed(final Subject subject, final Granularity granularity) {
        final Criteria criteria = criteriaForFeedAndGranularity(subject, granularity);
        return (List<Statistics>) criteria.list();
    }

    @Override
    public List<Statistics> forFeed(final Subject subject, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForFeedAndGranularity(subject, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return (List<Statistics>) criteria.list();
    }

    private Criteria criteriaForFeedAndGranularity(final Subject subject, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.eq("subjectId", subject.getId()));
        criteria.add(Restrictions.eq("granularity", granularity));
        return criteria;
    }
}
