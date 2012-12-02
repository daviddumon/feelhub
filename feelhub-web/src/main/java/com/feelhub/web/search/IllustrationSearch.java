package com.feelhub.web.search;

import com.feelhub.domain.meta.Illustration;
import com.feelhub.repositories.SessionProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class IllustrationSearch implements Search<Illustration> {

    @Inject
    public IllustrationSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Illustration.class);
    }

    @Override
    public List<Illustration> execute() {
        return criteria.list();
    }

    @Override
    public IllustrationSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public IllustrationSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public IllustrationSearch withSort(final String sortField, final int sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public IllustrationSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("topicId", topicId));
        return this;
    }

    public IllustrationSearch withTopicIds(final List<UUID> topics) {
        final List<String> topicAsString = Lists.newArrayList();
        for (final UUID topicId : topics) {
            topicAsString.add(topicId.toString());
        }
        criteria.add(Restrictions.in("topicId", topics));
        return this;
    }

    private final Criteria criteria;
}
