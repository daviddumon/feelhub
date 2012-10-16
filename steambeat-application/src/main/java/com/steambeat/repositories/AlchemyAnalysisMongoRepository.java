package com.steambeat.repositories;

import com.steambeat.domain.alchemy.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyAnalysisMongoRepository extends BaseMongoRepository<AlchemyAnalysis> implements AlchemyAnalysisRepository {

    public AlchemyAnalysisMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<AlchemyAnalysis> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(AlchemyAnalysis.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        return criteria.list();
    }
}
