package com.steambeat.domain.statistics;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import org.joda.time.DateTime;

public class StatisticsTestFactory {

    public Statistics newStatistics(final Reference reference, final Granularity granularity) {
        final Statistics statistics = new Statistics(reference.getId(), granularity, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatisticsWithJudgments(final Reference reference, final Granularity granularity) {
        final Statistics statistics = new Statistics(reference.getId(), granularity, new DateTime());
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.good));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.bad));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.bad));
        statistics.incrementJudgmentCount(new Judgment(reference.getId(), Feeling.neutral));
        new Judgment(reference.getId(), Feeling.good);
        Repositories.statistics().add(statistics);
        return statistics;
    }

    public Statistics newStatistics() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics statistics = new Statistics(reference.getId(), Granularity.day, new DateTime());
        Repositories.statistics().add(statistics);
        return statistics;
    }
}
