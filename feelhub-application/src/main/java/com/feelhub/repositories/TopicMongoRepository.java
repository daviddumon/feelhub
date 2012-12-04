package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class TopicMongoRepository extends BaseMongoRepository<Topic> implements TopicRepository {

    public TopicMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Topic world() {
        final Criteria criteria = getSession().createCriteria(Topic.class);
        criteria.add(Restrictions.equals("type", TopicType.World));
        final List list = criteria.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return (Topic) list.get(0);
        }
    }

    @Override
    public Topic getCurrentTopic(final UUID id) {
        Topic topic = get(id);
        while (!topic.getCurrentTopicId().equals(topic.getId())) {
            topic = get(topic.getCurrentTopicId());
        }
        return topic;
    }
}
