package com.steambeat.repositories;

import com.steambeat.domain.alchemy.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyEntityMongoRepository extends BaseMongoRepository<AlchemyEntity> implements AlchemyEntityRepository {

    public AlchemyEntityMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<AlchemyEntity> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(AlchemyEntity.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        return criteria.list();
    }
}
