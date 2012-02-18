package com.steambeat.application;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.statistics.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.*;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
    public void canRecordGoodJudgment() {
        final JudgmentPostedEvent event = new JudgmentPostedEvent(TestFactories.judgments().newGoodJudgment());

        statisticsService.judgmentOn(event);

        assertThat(getStatisticsRepository().forSubject(event.getJudgment().getSubject()).getGoodJudgments(), is(1));
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    @Test
    public void canRecordBadJudgment() {
        final JudgmentPostedEvent event = new JudgmentPostedEvent(TestFactories.judgments().newBadJudgment());

        statisticsService.judgmentOn(event);

        assertThat(getStatisticsRepository().forSubject(event.getJudgment().getSubject()).getBadJudgments(), is(1));
    }

    @Test
    public void canCreateFromEvent() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        DomainEventBus.INSTANCE.flush();

        assertThat(getStatisticsRepository().forSubject(event.getJudgment().getSubject()).getGoodJudgments(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        time.waitDays(1);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGoodJudgments(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2OpinionsForYear() {
        final JudgmentPostedEvent event = getGoodJudgmentEvent();
        DomainEventBus.INSTANCE.spread(event);

        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(),
                Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordOpinionForLastYear() {
        time.set(time.getNow().minusYears(3));
        final JudgmentPostedEvent event = getGoodJudgmentEvent();

        time.set(time.getNow().plusYears(3));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(),
                Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    private JudgmentPostedEvent getGoodJudgmentEvent() {
        final JudgmentPostedEvent event = new JudgmentPostedEvent(TestFactories.judgments().newGoodJudgment());
        DomainEventBus.INSTANCE.spread(event);
        return event;
    }

    @Test
    public void canRecordOpinionsForLastMonth() {
        time.set(time.getNow().minusMonths(3));
        final JudgmentPostedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusMonths(5));
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(new Judgment(event.getJudgment().getSubject(), Feeling.good)));

        time.set(time.getNow().plusMonths(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastDay() {
        time.set(time.getNow().minusDays(3));
        final JudgmentPostedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusDays(5));
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(new Judgment(event.getJudgment().getSubject(), Feeling.good)));

        time.set(time.getNow().plusDays(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastHour() {
        time.set(time.getNow().minusHours(3));
        final JudgmentPostedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(new Judgment(event.getJudgment().getSubject(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForSubject() {
        time.set(time.getNow().minusHours(3));
        final JudgmentPostedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        DomainEventBus.INSTANCE.spread(new JudgmentPostedEvent(new Judgment(event.getJudgment().getSubject(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        DomainEventBus.INSTANCE.flush();

        final List<Statistics> statistics = getStatisticsRepository().forSubject(event.getJudgment().getSubject(), Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGoodJudgments(), is(2));
    }

    @Test
    @Ignore
    public void canRecordStatsForAllSubject() {
        fail();
        //final Subject subject = TestFactories.webPages().newWebPage();
        //subject.createOpinion("my good opinion", Feeling.good);
        //time.set(time.getNow().plusYears(1));
        //
        //DomainEventBus.INSTANCE.flush();
        //
        //final WebPage steambeat = new WebPage(new Association(new Uri("steambeat"), null));
        //final List<Statistics> statistics = getStatisticsRepository().forSubject(steambeat, Granularity.all);
        //assertThat(statistics.size(), is(1));
    }

    private StatisticsService statisticsService;
}
