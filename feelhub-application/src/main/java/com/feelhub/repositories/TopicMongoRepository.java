package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import org.mongolink.MongoSession;

public class TopicMongoRepository extends BaseMongoRepository<Topic> implements TopicRepository {

    public TopicMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Topic getActive(Object id) {
        Topic topic = session.get(id, getPersistentType());
        while (!topic.isActive() && !topic.getCurrentTopicId().equals(id)) {
            id = topic.getCurrentTopicId();
            topic = session.get(id, getPersistentType());
        }
        return topic;
    }
}
