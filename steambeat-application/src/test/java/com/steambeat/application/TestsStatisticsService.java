package com.steambeat.application;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.*;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Ignore
public class TestsStatisticsService {

    @Rule
    public WithFakeRepositories repos = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        statisticsService = new StatisticsService();
    }

    @Test
    public void canRecordGoodOpinion() {
        final Subject subject = TestFactories.webPages().newWebPage();
        final Opinion opinion = subject.createOpinion("my good opinion", Feeling.good);

        statisticsService.opinionOn(subject, opinion);

        assertThat(getStatisticsRepository().forSubject(subject).getGoodOpinions(), is(1));
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    @Test
    public void canRecordBadOpinion() {
        final Subject subject = TestFactories.webPages().newWebPage();
        final Opinion opinion = subject.createOpinion("my bad opinion", Feeling.bad);

        statisticsService.opinionOn(subject, opinion);

        assertThat(getStatisticsRepository().forSubject(subject).getBadOpinions(), is(1));
    }

    @Test
    public void canCreateFromEvent() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        assertThat(getStatisticsRepository().forSubject(subject).getGoodOpinions(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);

        time.waitDays(1);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGoodOpinions(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2OpinionsForYear() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);
        subject.createOpinion("my good opinion", Feeling.good);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordOpinionForLastYear() {
        final Subject subject = TestFactories.webPages().newWebPage();
        time.set(time.getNow().minusYears(3));
        subject.createOpinion("my good opinion", Feeling.good);

        time.set(time.getNow().plusYears(3));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canRecordOpinionsForLastMonth() {
        final Subject subject = TestFactories.webPages().newWebPage();
        time.set(time.getNow().minusMonths(3));
        subject.createOpinion("my good opinion", Feeling.good);
        time.set(time.getNow().minusMonths(5));
        subject.createOpinion("my good opinion", Feeling.good);

        time.set(time.getNow().plusMonths(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastDay() {
        final Subject subject = TestFactories.webPages().newWebPage();
        time.set(time.getNow().minusDays(3));
        subject.createOpinion("my good opinion", Feeling.good);
        time.set(time.getNow().minusDays(5));
        subject.createOpinion("my good opinion", Feeling.good);

        time.set(time.getNow().plusDays(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastHour() {
        final Subject subject = TestFactories.webPages().newWebPage();
        time.set(time.getNow().minusHours(3));
        subject.createOpinion("my good opinion", Feeling.good);
        time.set(time.getNow().minusHours(5));
        subject.createOpinion("my good opinion", Feeling.good);

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForSubject() {
        final Subject subject = TestFactories.webPages().newWebPage();
        time.set(time.getNow().minusHours(3));
        subject.createOpinion("my good opinion", Feeling.good);
        time.set(time.getNow().minusHours(5));
        subject.createOpinion("my good opinion", Feeling.good);

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(subject, Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodOpinions(), is(2));
    }

    @Test
    public void canRecordStatsForAllSubject() {
        final Subject subject = TestFactories.webPages().newWebPage();
        subject.createOpinion("my good opinion", Feeling.good);
        time.set(time.getNow().plusYears(1));

        DomainEventBus.INSTANCE.flush();

        final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
        final List<Statistics> statistics = getStatisticsRepository().forSubject(steambeat, Granularity.all);
        assertThat(statistics.size(), is(1));
    }

    private StatisticsService statisticsService;
}
