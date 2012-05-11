package com.steambeat.repositories;

import com.steambeat.domain.association.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class AssociationMongoRepository extends BaseMongoRepository<Association> implements AssociationRepository {

    public AssociationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Association forIdentifier(final Identifier identifier) {
        final Criteria criteria = getSession().createCriteria(Association.class);
        criteria.add(Restrictions.equals("identifier", identifier.toString()));
        final List<Association> results = criteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
