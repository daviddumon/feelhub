package com.steambeat.web.search;

import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
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
        final WebPage webPage = TestFactories.subjects().newWebPage();
        final Statistics statistics = TestFactories.statistics().newStatistics(webPage, Granularity.all);
        TestFactories.statistics().newStatistics();

        final List<Statistics> statisticsList = statisticsSearch.withSubject(webPage).execute();

        assertThat(statisticsList.size(), is(1));
        assertThat(statisticsList.get(0), is(statistics));
    }

    @Test
    public void canGetAStatisticsForASubjectAndGranularity() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        TestFactories.statistics().newStatistics(webPage, Granularity.day);
        final Statistics statistics = TestFactories.statistics().newStatistics(webPage, Granularity.hour);

        final List<Statistics> statisticsList = statisticsSearch.withSubject(webPage).withGranularity(statistics.getGranularity()).execute();

        assertThat(statisticsList.size(), is(1));
    }

    @Test
    public void canGetAStatisticsForAnInterval() {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        TestFactories.statistics().newStatistics(webPage, Granularity.day);
        time.waitDays(2);
        final Statistics statistics = TestFactories.statistics().newStatistics(webPage, Granularity.day);

        final Interval interval = Granularity.hour.intervalFor(statistics.getDate());
        final List<Statistics> statisticsList = statisticsSearch.withInterval(interval).execute();

        assertThat(statisticsList.size(), is(1));
    }

    private StatisticsSearch statisticsSearch;
}
