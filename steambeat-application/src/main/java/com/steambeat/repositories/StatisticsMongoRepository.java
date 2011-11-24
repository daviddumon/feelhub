package com.steambeat.repositories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
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
    public List<Statistics> forSubject(final Subject subject, final Granularity granularity) {
        final Criteria criteria = criteriaForSubjectAndGranularity(subject, granularity);
        return (List<Statistics>) criteria.list();
    }

    @Override
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
