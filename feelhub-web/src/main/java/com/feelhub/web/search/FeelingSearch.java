package com.feelhub.web.search;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.repositories.SessionProvider;
import org.mongolink.domain.criteria.*;

import javax.inject.Inject;
import java.util.*;

public class FeelingSearch implements Search<Feeling> {

    @Inject
    public FeelingSearch(final SessionProvider provider) {
        this.provider = provider;
        criteria = provider.get().createCriteria(Feeling.class);
        criteria.add(Restrictions.notEquals("text", ""));
    }

    @Override
    public List<Feeling> execute() {
        return (List<Feeling>) criteria.list();
    }

    @Override
    public FeelingSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public FeelingSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public FeelingSearch withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public FeelingSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("sentiments.topicId", topicId));
        return this;
    }

    public void reset() {
        criteria = provider.get().createCriteria(Feeling.class);
        criteria.add(Restrictions.notEquals("text", ""));
    }

    private Criteria criteria;
    private final SessionProvider provider;
}
