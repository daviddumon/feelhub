package com.steambeat.domain.statistics;

import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import org.joda.time.DateTime;

public class StatisticsTestFactory {

    public Statistics newStatistics(final Topic topic, final Granularity granularity) {
        final Statistics statistics = new Statistics(topic, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics statistics = new Statistics(topic, Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
