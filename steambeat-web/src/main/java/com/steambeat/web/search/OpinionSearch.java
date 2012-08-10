package com.steambeat.web.search;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.SessionProvider;
import org.mongolink.domain.criteria.*;

import javax.inject.Inject;
import java.util.List;

public class OpinionSearch implements Search<Opinion> {

    @Inject
    public OpinionSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Opinion.class);
        criteria.add(Restrictions.notEquals("text", ""));
    }

    @Override
    public List<Opinion> execute() {
        return (List<Opinion>) criteria.list();
    }

    @Override
    public OpinionSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    @Override
    public OpinionSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    @Override
    public OpinionSearch withSort(final String sortField, final int sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    public OpinionSearch withTopic(final Topic topic) {
        criteria.add(Restrictions.equals("judgments.topicId", topic.getId()));
        return this;
    }

    private final Criteria criteria;
}
