package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.statistics.*;
import org.joda.time.Interval;

import java.util.List;

public class FakeStatisticsRepository extends FakeRepository<Statistics> implements StatisticsRepository {

    public Statistics forReference(final Reference reference) {
        return Iterables.find(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(reference.getId());
            }
        });
    }

    @Override
    public List<Statistics> forReference(final Reference reference, final Granularity granularity) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(reference.getId()) && input.getGranularity() == granularity;
            }
        }));
    }

    @Override
    public List<Statistics> forReference(final Reference reference, final Granularity granularity, final Interval interval) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(reference.getId()) && input.getGranularity() == granularity && interval.contains(input.getDate());
            }
        }));
    }
}
