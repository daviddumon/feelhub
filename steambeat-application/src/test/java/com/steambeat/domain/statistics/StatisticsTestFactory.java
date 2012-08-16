package com.steambeat.domain.statistics;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import org.joda.time.DateTime;

public class StatisticsTestFactory {

    public Statistics newStatistics(final Reference reference, final Granularity granularity) {
        final Statistics statistics = new Statistics(reference, granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics statistics = new Statistics(reference, Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
