package com.steambeat.web.search;

import com.google.inject.Inject;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.SessionProvider;
import org.joda.time.Interval;
import org.mongolink.domain.criteria.*;

import java.util.List;

public class StatisticsSearch {

    @Inject
    public StatisticsSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Statistics.class);
    }

    @SuppressWarnings("unchecked")
    public List<Statistics> execute() {
        return criteria.list();
    }

    public StatisticsSearch withSubject(final Subject subject) {
        criteria.add(Restrictions.equals("subjectId", subject.getId()));
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