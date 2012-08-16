package com.steambeat.web.search.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.*;
import com.steambeat.web.search.StatisticsSearch;
import org.joda.time.Interval;

import java.util.List;

public class FakeStatisticsSearch extends StatisticsSearch {

    @Inject
    public FakeStatisticsSearch(final SessionProvider provider) {
        super(provider);
        statisticsList = Repositories.statistics().getAll();
    }

    @Override
    public StatisticsSearch withInterval(final Interval interval) {
        statisticsList = Lists.newArrayList(Iterables.filter(statisticsList, new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics statistics) {
                if (interval.contains(statistics.getDate().getMillis())) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    @Override
    public StatisticsSearch withGranularity(final Granularity granularity) {
        statisticsList = Lists.newArrayList(Iterables.filter(statisticsList, new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics statistics) {
                if (statistics.getGranularity().equals(granularity)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    @Override
    public StatisticsSearch withTopic(final Reference reference) {
        statisticsList = Lists.newArrayList(Iterables.filter(statisticsList, new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics statistics) {
                if (statistics.getReferenceId().equals(reference.getId())) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    @Override
    public List<Statistics> execute() {
        return statisticsList;
    }

    private List<Statistics> statisticsList;
}
