package com.steambeat.repositories;

import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class KeywordMongoRepository extends BaseMongoRepository<Keyword> implements KeywordRepository {

    public KeywordMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Keyword forValueAndLanguage(final String value, final SteambeatLanguage steambeatLanguage) {
        final Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(Restrictions.equals("value", value));
        criteria.add(Restrictions.equals("languageCode", steambeatLanguage.getCode()));
        final List<Keyword> results = criteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<Keyword> forReferenceId(final UUID referenceId) {
        final Criteria criteria = getSession().createCriteria(Keyword.class);
        criteria.add(Restrictions.equals("referenceId", referenceId));
        return criteria.list();
    }
}
