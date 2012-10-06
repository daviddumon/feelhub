package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.statistics.*;
import org.joda.time.Interval;

import java.util.*;

public class FakeStatisticsRepository extends FakeRepository<Statistics> implements StatisticsRepository {

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId, final Granularity granularity) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(referenceId) && input.getGranularity() == granularity;
            }
        }));
    }

    @Override
    public List<Statistics> forReferenceId(final UUID referenceId, final Granularity granularity, final Interval interval) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getReferenceId().equals(referenceId) && input.getGranularity() == granularity && interval.contains(input.getDate());
            }
        }));
    }
}
