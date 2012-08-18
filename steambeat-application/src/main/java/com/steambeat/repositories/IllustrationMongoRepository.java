package com.steambeat.repositories;

import com.steambeat.domain.illustration.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class IllustrationMongoRepository extends BaseMongoRepository<Illustration> implements IllustrationRepository {

    public IllustrationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<Illustration> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(Illustration.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        final List<Illustration> results = criteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }
}
