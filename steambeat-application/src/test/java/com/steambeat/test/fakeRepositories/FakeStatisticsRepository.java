package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.topic.Topic;
import org.joda.time.Interval;

import java.util.List;

public class FakeStatisticsRepository extends FakeRepository<Statistics> implements StatisticsRepository {

    public Statistics forTopic(final Topic topic) {
        return Iterables.find(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topic.getId());
            }
        });
    }

    @Override
    public List<Statistics> forTopic(final Topic topic, final Granularity granularity) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topic.getId()) && input.getGranularity() == granularity;
            }
        }));
    }

    @Override
    public List<Statistics> forTopic(final Topic topic, final Granularity granularity, final Interval interval) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Statistics>() {

            @Override
            public boolean apply(final Statistics input) {
                return input.getTopicId().equals(topic.getId()) && input.getGranularity() == granularity && interval.contains(input.getDate());
            }
        }));
    }
}
