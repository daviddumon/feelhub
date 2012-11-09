package com.feelhub.web.search.fake;

import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.*;
import com.feelhub.web.search.StatisticsSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
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
    public StatisticsSearch withTopic(final Topic topic) {
        statisticsList = Lists.newArrayList(Iterables.filter(statisticsList, new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics statistics) {
                if (statistics.getTopicId().equals(topic.getId())) {
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
