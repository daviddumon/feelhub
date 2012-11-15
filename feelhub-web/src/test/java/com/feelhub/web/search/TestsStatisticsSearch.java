package com.feelhub.web.search;

import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.statistics.*;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
import org.joda.time.Interval;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;


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

        assertThat(statisticsList).isNotNull();
        assertThat(statisticsList.size()).isZero();
    }

    @Test
    public void canGetAStatisticsWithATopicId() {
        final Word word = TestFactories.keywords().newWord();
        final Statistics statistics = TestFactories.statistics().newStatistics(word.getTopicId(), Granularity.all);
        TestFactories.statistics().newStatistics();

        final List<Statistics> statisticsList = statisticsSearch.withTopicId(word.getTopicId()).execute();

        assertThat(statisticsList.size()).isEqualTo(1);
        assertThat(statisticsList.get(0)).isEqualTo(statistics);
    }

    @Test
    public void canGetAStatisticsForATopicAndGranularity() {
        final Word word = TestFactories.keywords().newWord();
        TestFactories.statistics().newStatistics(word.getTopicId(), Granularity.day);
        final Statistics statistics = TestFactories.statistics().newStatistics(word.getTopicId(), Granularity.hour);
        TestFactories.statistics().newStatistics(UUID.randomUUID(), Granularity.hour);

        final List<Statistics> statisticsList = statisticsSearch.withTopicId(word.getTopicId()).withGranularity(statistics.getGranularity()).execute();

        assertThat(statisticsList.size()).isEqualTo(1);
    }

    @Test
    public void canGetAStatisticsForAnInterval() {
        final Word word = TestFactories.keywords().newWord();
        TestFactories.statistics().newStatistics(word.getTopicId(), Granularity.day);
        time.waitDays(2);
        final Statistics statistics = TestFactories.statistics().newStatistics(word.getTopicId(), Granularity.day);

        final Interval interval = Granularity.hour.intervalFor(statistics.getDate());
        final List<Statistics> statisticsList = statisticsSearch.withInterval(interval).execute();

        assertThat(statisticsList.size()).isEqualTo(1);
    }

    private StatisticsSearch statisticsSearch;
}
