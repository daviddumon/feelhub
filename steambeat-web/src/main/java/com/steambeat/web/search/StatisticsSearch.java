package com.steambeat.web.search;

import com.google.inject.Inject;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.SessionProvider;
import org.joda.time.Interval;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class StatisticsSearch implements Search<Statistics> {

    @Inject
    public StatisticsSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Statistics.class);
    }

    @Override
    public List<Statistics> execute() {
        return criteria.list();
    }

    @Override
    public StatisticsSearch withSkip(final int skip) {
        return null;
    }

    @Override
    public StatisticsSearch withLimit(final int limit) {
        return null;
    }

    @Override
    public StatisticsSearch withSort(final String sortField, final int sortOrder) {
        return null;
    }

    public StatisticsSearch withTopic(final Topic topic) {
        criteria.add(Restrictions.equals("topicId", topic.getId()));
        return this;
    }

    public StatisticsSearch withGranularity(final Granularity granularity) {
        criteria.add(Restrictions.equals("granularity", granularity.toString()));
        return this;
    }

    public StatisticsSearch withInterval(final Interval interval) {
        criteria.add(Restrictions.between("date", interval.getStart(), interval.getEnd()));
        return this;
    }

    private final Criteria criteria;
}
