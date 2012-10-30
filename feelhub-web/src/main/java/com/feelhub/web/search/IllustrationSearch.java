package com.feelhub.web.search;

import com.feelhub.domain.illustration.Illustration;
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
    public Search<Illustration> withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public Search<Illustration> withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public Search<Illustration> withSort(final String sortField, final int sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    public Search<Illustration> withReferences(final List<UUID> references) {
        final List<String> referencesAsString = Lists.newArrayList();
        for (final UUID reference : references) {
            referencesAsString.add(reference.toString());
        }
        criteria.add(Restrictions.inUUID("referenceId", references));
        return this;
    }

    private final Criteria criteria;
}
