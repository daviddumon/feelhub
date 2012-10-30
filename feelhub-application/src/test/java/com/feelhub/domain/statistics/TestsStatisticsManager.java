package com.feelhub.domain.statistics;

import com.feelhub.domain.reference.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
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
    public void canChangeStatisticsReferences() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        TestFactories.statistics().newStatisticsWithJudgments(ref1, Granularity.all);
        TestFactories.statistics().newStatisticsWithJudgments(ref1, Granularity.day);
        TestFactories.statistics().newStatisticsWithJudgments(ref1, Granularity.hour);
        TestFactories.statistics().newStatisticsWithJudgments(ref2, Granularity.hour);
        TestFactories.statistics().newStatisticsWithJudgments(ref2, Granularity.month);
        final ReferencePatch referencePatch = new ReferencePatch(ref1.getId());
        referencePatch.addOldReferenceId(ref2.getId());

        statisticsManager.merge(referencePatch);

        assertThat(Repositories.statistics().getAll().size(), is(4));
        final List<Statistics> all = Repositories.statistics().forReferenceId(ref1.getId(), Granularity.all);
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getGood(), is(3));
        assertThat(all.get(0).getBad(), is(2));
        assertThat(all.get(0).getNeutral(), is(1));
        final List<Statistics> day = Repositories.statistics().forReferenceId(ref1.getId(), Granularity.day);
        assertThat(day.size(), is(1));
        assertThat(day.get(0).getGood(), is(3));
        assertThat(day.get(0).getBad(), is(2));
        assertThat(day.get(0).getNeutral(), is(1));
        final List<Statistics> hour = Repositories.statistics().forReferenceId(ref1.getId(), Granularity.hour);
        assertThat(hour.size(), is(1));
        assertThat(hour.get(0).getGood(), is(6));
        assertThat(hour.get(0).getBad(), is(4));
        assertThat(hour.get(0).getNeutral(), is(2));
        final List<Statistics> month = Repositories.statistics().forReferenceId(ref1.getId(), Granularity.month);
        assertThat(month.size(), is(1));
        assertThat(month.get(0).getGood(), is(3));
        assertThat(month.get(0).getBad(), is(2));
        assertThat(month.get(0).getNeutral(), is(1));
    }

    private StatisticsManager statisticsManager;
}
