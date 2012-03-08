package com.steambeat.repositories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import org.joda.time.Interval;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;


public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Statistics> forSubject(final Subject subject, final Granularity granularity) {
        final Criteria criteria = criteriaForSubjectAndGranularity(subject, granularity);
        return (List<Statistics>) criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Statistics> forSubject(final Subject subject, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForSubjectAndGranularity(subject, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return (List<Statistics>) criteria.list();
    }

    private Criteria criteriaForSubjectAndGranularity(final Subject subject, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("subjectId", subject.getId()));
        criteria.add(Restrictions.equals("granularity", granularity));
        return criteria;
    }
}
