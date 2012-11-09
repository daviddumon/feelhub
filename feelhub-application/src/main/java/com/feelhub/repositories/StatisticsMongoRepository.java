package com.feelhub.repositories;

import com.feelhub.domain.statistics.*;
import org.joda.time.Interval;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;


public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Statistics> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }

    @Override
    public List<Statistics> forTopicId(final UUID topicId, final Granularity granularity) {
        final Criteria criteria = criteriaForTopicAndGranularity(topicId, granularity);
        return criteria.list();
    }

    @Override
    public List<Statistics> forTopicId(final UUID topicId, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForTopicAndGranularity(topicId, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return criteria.list();
    }

    private Criteria criteriaForTopicAndGranularity(final UUID topicId, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        criteria.add(Restrictions.equals("granularity", granularity));
        return criteria;
    }
}
