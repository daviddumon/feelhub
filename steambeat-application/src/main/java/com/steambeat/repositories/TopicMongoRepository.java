package com.steambeat.repositories;

import com.steambeat.domain.topic.Topic;
import org.mongolink.MongoSession;

public class TopicMongoRepository extends BaseMongoRepository<Topic> {

    public TopicMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }
}
