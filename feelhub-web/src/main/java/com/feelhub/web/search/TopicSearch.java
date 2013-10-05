package com.feelhub.web.search;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.SessionProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class TopicSearch implements Search<Topic> {

    @Inject
    public TopicSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Topic.class);
    }

    @Override
    public List<Topic> execute() {
        return criteria.list();
    }

    @Override
    public Search<Topic> withSkip(final int skipValue) {
        criteria.skip(skipValue);
        return this;
    }

    @Override
    public Search<Topic> withLimit(final int limitValue) {
        criteria.limit(limitValue);
        return this;
    }

    @Override
    public Search<Topic> withSort(final String sortField, final Order sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    @Override
    public Search<Topic> withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("_id", topicId));
        return this;
    }

    public Search<Topic> withCurrentId(final UUID currentId) {
        criteria.add(Restrictions.equals("currentId", currentId));
        return this;
    }

    public Search<Topic> forFeelings(final List<Feeling> feelings) {
        final List<UUID> currentIds = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            currentIds.add(feeling.getTopicId());
        }
        criteria.add(Restrictions.in("currentId", currentIds));
        return this;
    }

    private final Criteria criteria;
}
