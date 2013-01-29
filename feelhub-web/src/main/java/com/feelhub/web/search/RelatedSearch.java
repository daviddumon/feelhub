package com.feelhub.web.search;

import com.feelhub.domain.related.Related;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class RelatedSearch implements Search<Related> {

    @Inject
    public RelatedSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Related.class);
    }

    @Override
    public List<Related> execute() {
        return criteria.list();
    }

    @Override
    public RelatedSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public RelatedSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public RelatedSearch withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public RelatedSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("fromId", topicId));
        return this;
    }

    private final Criteria criteria;
}
