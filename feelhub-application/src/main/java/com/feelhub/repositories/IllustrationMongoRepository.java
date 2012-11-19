package com.feelhub.repositories;

import com.feelhub.domain.meta.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class IllustrationMongoRepository extends BaseMongoRepository<Illustration> implements IllustrationRepository {

    public IllustrationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Illustration> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(Illustration.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }
}
