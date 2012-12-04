package com.feelhub.repositories;

import com.feelhub.domain.tag.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class TagMongoRepository extends BaseMongoRepository<Tag> implements TagRepository {

    public TagMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Tag> forTopicId(final UUID topicId) {
        final Criteria<Tag> criteria = createCriteria();
        criteria.add(Restrictions.equals("topicIds", topicId));
        return criteria.list();
    }
}
