package com.feelhub.web.search;

import com.feelhub.domain.media.Media;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class MediaSearch implements Search<Media> {

    @Inject
    public MediaSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Media.class);
    }

    @Override
    public List<Media> execute() {
        return criteria.list();
    }

    @Override
    public MediaSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public MediaSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public MediaSearch withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public MediaSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("fromId", topicId));
        return this;
    }

    private final Criteria criteria;
}
