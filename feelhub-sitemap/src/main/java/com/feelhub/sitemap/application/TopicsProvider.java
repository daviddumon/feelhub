package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import org.mongolink.MongoSession;

import java.util.List;

public class TopicsProvider {
    public List<Topic> topics(final MongoSession session) {
        session.start();
        final List result = session.createCriteria(Topic.class).list();
        session.stop();
        return result;
    }
}
