package com.steambeat.repositories;

import com.steambeat.domain.alchemy.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyMongoRepository extends BaseMongoRepository<Alchemy> implements AlchemyRepository {

    public AlchemyMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Alchemy> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(Alchemy.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        return criteria.list();
    }
}
