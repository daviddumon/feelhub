package com.steambeat.repositories;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.topic.Topic;
import org.joda.time.Interval;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;


public class StatisticsMongoRepository extends BaseMongoRepository<Statistics> implements StatisticsRepository {

    public StatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Statistics> forTopic(final Topic topic, final Granularity granularity) {
        final Criteria criteria = criteriaForTopicAndGranularity(topic, granularity);
        return (List<Statistics>) criteria.list();
    }

    @Override
    public List<Statistics> forTopic(final Topic topic, final Granularity granularity, final Interval interval) {
        final Criteria criteria = criteriaForTopicAndGranularity(topic, granularity);
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return (List<Statistics>) criteria.list();
    }

    private Criteria criteriaForTopicAndGranularity(final Topic topic, final Granularity granularity) {
        final Criteria criteria = getSession().createCriteria(Statistics.class);
        criteria.add(Restrictions.equals("topicId", topic.getId()));
        criteria.add(Restrictions.equals("granularity", granularity));
        return criteria;
    }
}
