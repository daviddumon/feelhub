package com.steambeat.application;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.test.fakeRepositories.*;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.test.*;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsStatisticsService {

    @Rule
    public WithFakeRepositories repos = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        statisticsService = new StatisticsService();
    }

    @Test
    public void canRecordGoodOpinion() {
        final Feed feed = TestFactories.feeds().newFeed();
        final Opinion opinion = feed.createOpinion("ok", Feeling.good);

        statisticsService.opinionOn(feed, opinion);

        assertThat(getStatisticsRepository().forFeed(feed).getGoodOpinions(), is(1));
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    @Test
    public void canRecordBadOpinion() {
        final Feed feed = TestFactories.feeds().newFeed();
        final Opinion opinion = feed.createOpinion("not ok", Feeling.bad);

        statisticsService.opinionOn(feed, opinion);

        assertThat(getStatisticsRepository().forFeed(feed).getBadOpinions(), is(1));
    }

    @Test
    public void canRecordNeutralOpinion() {
        final Feed feed = TestFactories.feeds().newFeed();
        final Opinion opinion = feed.createOpinion("not ok", Feeling.neutral);

        statisticsService.opinionOn(feed, opinion);

        assertThat(getStatisticsRepository().forFeed(feed).getNeutralOpinions(), is(1));
    }

    @Test
    public void canCreateFromEvent() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        assertThat(getStatisticsRepository().forFeed(feed).getGoodOpinions(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);

        time.waitDays(1);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2OpinionsForYear() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);
        feed.createOpinion("opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordOpinionForLastYear() {
        final Feed feed = TestFactories.feeds().newFeed();
        time.set(time.getNow().minusYears(3));
        feed.createOpinion("opinion", Feeling.good);

        time.set(time.getNow().plusYears(3));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canRecordOpinionsForLastMonth() {
        final Feed feed = TestFactories.feeds().newFeed();
        time.set(time.getNow().minusMonths(3));
        feed.createOpinion("opinion", Feeling.good);
        time.set(time.getNow().minusMonths(5));
        feed.createOpinion("opinion", Feeling.good);

        time.set(time.getNow().plusMonths(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastDay() {
        final Feed feed = TestFactories.feeds().newFeed();
        time.set(time.getNow().minusDays(3));
        feed.createOpinion("opinion", Feeling.good);
        time.set(time.getNow().minusDays(5));
        feed.createOpinion("opinion", Feeling.good);

        time.set(time.getNow().plusDays(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastHour() {
        final Feed feed = TestFactories.feeds().newFeed();
        time.set(time.getNow().minusHours(3));
        feed.createOpinion("opinion", Feeling.good);
        time.set(time.getNow().minusHours(5));
        feed.createOpinion("opinion", Feeling.good);

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForFeed() {
        final Feed feed = TestFactories.feeds().newFeed();
        time.set(time.getNow().minusHours(3));
        feed.createOpinion("opinion", Feeling.good);
        time.set(time.getNow().minusHours(5));
        feed.createOpinion("opinion", Feeling.good);

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forFeed(feed, Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodOpinions(), is(2));
    }

    @Test
    public void canRecordStatsForAllFeed() {
        final Feed feed = TestFactories.feeds().newFeed();
        feed.createOpinion("opinion", Feeling.good);
        time.set(time.getNow().plusYears(1));

        DomainEventBus.INSTANCE.flush();

        final Feed steambeat = new Feed(new Association(new Uri("steambeat"), null));
        final List<Statistics> statistics = getStatisticsRepository().forFeed(steambeat, Granularity.all);
        assertThat(statistics.size(), is(1));
    }

    private StatisticsService statisticsService;
}
