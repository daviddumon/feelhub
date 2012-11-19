package com.feelhub.repositories;

import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class TagMongoRepository extends BaseMongoRepository<Tag> implements TagRepository {

    public TagMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Tag forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage) {
        final Criteria criteria = getSession().createCriteria(Tag.class);
        criteria.add(Restrictions.equals("value", value));
        criteria.add(Restrictions.equals("languageCode", feelhubLanguage.getCode()));
        return extractOne(criteria);
    }

    @Override
    public Tag forTopicIdAndLanguage(final UUID topicId, final FeelhubLanguage feelhubLanguage) {
        final Criteria criteria = getSession().createCriteria(Tag.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        criteria.add(Restrictions.equals("languageCode", feelhubLanguage.getCode()));
        return extractOne(criteria);
    }

    @Override
    public List<Tag> forTopicId(final UUID topicId) {
        final Criteria criteria = getSession().createCriteria(Tag.class);
        criteria.add(Restrictions.equals("topicId", topicId));
        return criteria.list();
    }
}
