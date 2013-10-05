package com.feelhub.web.search;

import com.feelhub.domain.statistics.*;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.joda.time.Interval;
import org.mongolink.domain.criteria.*;

import java.util.*;

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
    public StatisticsSearch withSkip(final int skipValue) {
        return null;
    }

    @Override
    public StatisticsSearch withLimit(final int limitValue) {
        return null;
    }

    @Override
    public StatisticsSearch withSort(final String sortField, final Order sortOrder) {
        return null;
    }

    @Override
    public StatisticsSearch withTopicId(final UUID topicId) {
        criteria.add(Restrictions.equals("topicId", topicId));
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
