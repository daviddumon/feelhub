package com.steambeat.domain.statistics;

import com.google.common.collect.Lists;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        statisticsManager = new StatisticsManager();
    }

    @Test
    @Ignore
    public void canChangeStatisticsReferencesForAConcept() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        TestFactories.statistics().newStatistics(ref1, Granularity.all);
        TestFactories.statistics().newStatistics(ref1, Granularity.day);
        TestFactories.statistics().newStatistics(ref1, Granularity.hour);
        TestFactories.statistics().newStatistics(ref2, Granularity.hour);
        TestFactories.statistics().newStatistics(ref2, Granularity.month);

        statisticsManager.migrate(ref1.getId(), Lists.newArrayList(ref2.getId()));

        final List<Statistics> statistics = Repositories.statistics().forReferenceId(ref1.getId());
        assertThat(statistics.size(), is(5));
    }

    private StatisticsManager statisticsManager;
}
