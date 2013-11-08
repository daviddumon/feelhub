package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class TopicsProvider {
    public List<Topic> topics(final MongoSession session) {
        session.start();
        final Criteria criteria = session.createCriteria(Topic.class);
        criteria.add(Restrictions.equals("hasFeelings", true));
        final List result = criteria.list();
        session.stop();
        return result;
    }
}
