package com.feelhub.web.search;

import com.feelhub.domain.relation.Relation;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class RelationSearch implements Search<Relation> {

    @Inject
    public RelationSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Relation.class);
    }

    @Override
    public List<Relation> execute() {
        return criteria.list();
    }

    @Override
    public RelationSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public RelationSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public RelationSearch withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public RelationSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("fromId", topicId));
        return this;
    }

    private final Criteria criteria;
}
