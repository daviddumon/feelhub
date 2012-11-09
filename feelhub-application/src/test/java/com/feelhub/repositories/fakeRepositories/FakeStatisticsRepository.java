package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.statistics.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.joda.time.Interval;

import java.util.*;

public class FakeStatisticsRepository extends FakeRepository<Statistics> implements StatisticsRepository {

    @Override
    public List<Statistics> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topicId);
            }
        }));
    }

    @Override
    public List<Statistics> forTopicId(final UUID topicId, final Granularity granularity) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topicId) && input.getGranularity() == granularity;
            }
        }));
    }

    @Override
    public List<Statistics> forTopicId(final UUID topicId, final Granularity granularity, final Interval interval) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topicId) && input.getGranularity() == granularity && interval.contains(input.getDate());
            }
        }));
    }
}
