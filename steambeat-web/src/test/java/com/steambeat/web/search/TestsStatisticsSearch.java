package com.steambeat.web.search;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.*;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsSearch extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        statisticsSearch = new StatisticsSearch(getProvider());
    }

    @Test
    public void canGetWithNoStatistics() {
        final List<Statistics> statisticsList = statisticsSearch.execute();

        assertThat(statisticsList, notNullValue());
        assertThat(statisticsList.size(), is(0));
    }

    @Test
    public void canGetAStatisticsWithASubjectId() {
        final Reference reference = TestFactories.references().newReference();
        final Statistics statistics = TestFactories.statistics().newStatistics(reference, Granularity.all);
        TestFactories.statistics().newStatistics();

        final List<Statistics> statisticsList = statisticsSearch.withTopic(reference).execute();

        assertThat(statisticsList.size(), is(1));
        assertThat(statisticsList.get(0), is(statistics));
    }

    @Test
    public void canGetAStatisticsForASubjectAndGranularity() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.statistics().newStatistics(reference, Granularity.day);
        final Statistics statistics = TestFactories.statistics().newStatistics(reference, Granularity.hour);

        final List<Statistics> statisticsList = statisticsSearch.withTopic(reference).withGranularity(statistics.getGranularity()).execute();

        assertThat(statisticsList.size(), is(1));
    }

    @Test
    public void canGetAStatisticsForAnInterval() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.statistics().newStatistics(reference, Granularity.day);
        time.waitDays(2);
        final Statistics statistics = TestFactories.statistics().newStatistics(reference, Granularity.day);

        final Interval interval = Granularity.hour.intervalFor(statistics.getDate());
        final List<Statistics> statisticsList = statisticsSearch.withInterval(interval).execute();

        assertThat(statisticsList.size(), is(1));
    }

    private StatisticsSearch statisticsSearch;
}
