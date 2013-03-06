package com.feelhub.sitemap.application;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;
import org.mongolink.MongoSession;

import java.util.List;

public class TopicsProvider {
    public List<RealTopic> topics(MongoSession session) {
        session.start();
        List result = session.createCriteria(Topic.class).list();
        session.stop();
        return result;
    }
}
