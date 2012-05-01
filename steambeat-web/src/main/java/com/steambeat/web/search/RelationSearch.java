package com.steambeat.web.search;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.SessionProvider;
import org.mongolink.domain.criteria.*;

import java.util.List;

@SuppressWarnings("unchecked")
public class RelationSearch implements Search<Relation>{

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
    public RelationSearch withSort(final String sortField, final int sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    public RelationSearch withFrom(final Subject from) {
        criteria.add(Restrictions.equals("fromId", from.getId()));
        return this;
    }

    private final Criteria criteria;
}
