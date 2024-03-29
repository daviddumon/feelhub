package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyEntityMongoRepository extends BaseMongoRepository<AlchemyEntity> implements AlchemyEntityRepository {

    public AlchemyEntityMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<AlchemyEntity> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(AlchemyEntity.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }
}
