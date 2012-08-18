package com.steambeat.repositories;

import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.relation.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class RelationMongoRepository extends BaseMongoRepository<Relation> implements RelationRepository {

    public RelationMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Relation lookUp(final Reference from, final Reference to) {
        final Criteria criteria = getSession().createCriteria(Relation.class);
        criteria.add(Restrictions.equals("fromId", from.getId()));
        criteria.add(Restrictions.equals("toId", to.getId()));
        final List<Relation> relations = criteria.list();
        if (relations.isEmpty()) {
            return null;
        } else {
            return relations.get(0);
        }
    }
}
