package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyAnalysisMongoRepository extends BaseMongoRepository<AlchemyAnalysis> implements AlchemyAnalysisRepository {

    public AlchemyAnalysisMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public List<AlchemyAnalysis> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(AlchemyAnalysis.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }
}
