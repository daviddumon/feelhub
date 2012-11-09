package com.feelhub.web.search;

import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
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
    public void canGetAStatisticsWithATopicId() {
        final Topic topic = TestFactories.topics().newTopic();
        final Statistics statistics = TestFactories.statistics().newStatistics(topic, Granularity.all);
        TestFactories.statistics().newStatistics();

        final List<Statistics> statisticsList = statisticsSearch.withTopic(topic).execute();

        assertThat(statisticsList.size(), is(1));
        assertThat(statisticsList.get(0), is(statistics));
    }

    @Test
    public void canGetAStatisticsForATopicAndGranularity() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatistics(topic, Granularity.day);
        final Statistics statistics = TestFactories.statistics().newStatistics(topic, Granularity.hour);

        final List<Statistics> statisticsList = statisticsSearch.withTopic(topic).withGranularity(statistics.getGranularity()).execute();

        assertThat(statisticsList.size(), is(1));
    }

    @Test
    public void canGetAStatisticsForAnInterval() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.statistics().newStatistics(topic, Granularity.day);
        time.waitDays(2);
        final Statistics statistics = TestFactories.statistics().newStatistics(topic, Granularity.day);

        final Interval interval = Granularity.hour.intervalFor(statistics.getDate());
        final List<Statistics> statisticsList = statisticsSearch.withInterval(interval).execute();

        assertThat(statisticsList.size(), is(1));
    }

    private StatisticsSearch statisticsSearch;
}
