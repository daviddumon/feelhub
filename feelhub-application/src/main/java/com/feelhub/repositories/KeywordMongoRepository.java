package com.feelhub.repositories;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class KeywordMongoRepository extends BaseMongoRepository<Keyword> implements KeywordRepository {

    public KeywordMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Keyword forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage) {
        final Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(Restrictions.equals("value", value));
        criteria.add(Restrictions.equals("languageCode", feelhubLanguage.getCode()));
        return extractOne(criteria);
    }

    @Override
    public List<Keyword> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }

    @Override
    public World world() {
        final Criteria criteria = getSession().createCriteria(World.class);
        final List<World> results = criteria.list();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }
}
