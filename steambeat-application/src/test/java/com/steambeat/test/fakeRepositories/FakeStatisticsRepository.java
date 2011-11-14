package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.statistics.*;
import org.joda.time.Interval;

import java.util.List;

public class FakeStatisticsRepository extends FakeRepository<Statistics> implements StatisticsRepository {

    public Statistics forFeed(final Subject subject) {
        return Iterables.find(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(Statistics input) {
                return input.getSubjectId().equals(subject.getId());
            }
        });
    }

    @Override
    public List<Statistics> forFeed(final Subject subject, final Granularity granularity) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(Statistics input) {
                return input.getSubjectId().equals(subject.getId()) && input.getGranularity() == granularity;
            }
        }));
    }

    @Override
    public List<Statistics> forFeed(final Subject subject, final Granularity granularity, final Interval interval) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(Statistics input) {
                return input.getSubjectId().equals(subject.getId()) && input.getGranularity() == granularity && interval.contains(input.getDate());
            }
        }));
    }
}
