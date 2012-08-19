package com.steambeat.domain.statistics;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.*;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsFactory {

    @Rule
    public WithFakeRepositories repos = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        statisticsFactory = new StatisticsFactory();
        //Repositories.subjects().add(new Steam(UUID.randomUUID()));
    }

    @Test
    public void canRecordGoodJudgment() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReference(event.getJudgment().getReference()).getGood(), is(1));
    }

    @Test
    public void canRecordBadJudgment() {
        final JudgmentCreatedEvent event = getBadJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReference(event.getJudgment().getReference()).getBad(), is(1));
    }

    @Test
    public void canCreateFromEvent() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReference(event.getJudgment().getReference()).getGood(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        time.waitDays(1);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGood(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2OpinionsForYear() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();
        DomainEventBus.INSTANCE.post(event);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(),
                Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordOpinionForLastYear() {
        time.set(time.getNow().minusYears(3));
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        time.set(time.getNow().plusYears(3));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(),
                Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canRecordOpinionsForLastMonth() {
        time.set(time.getNow().minusMonths(3));
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusMonths(5));
        statisticsFactory.handle(new JudgmentCreatedEvent(new Judgment(event.getJudgment().getReference(), Feeling.good)));

        time.set(time.getNow().plusMonths(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastDay() {
        time.set(time.getNow().minusDays(3));
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusDays(5));
        statisticsFactory.handle(new JudgmentCreatedEvent(new Judgment(event.getJudgment().getReference(), Feeling.good)));

        time.set(time.getNow().plusDays(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastHour() {
        time.set(time.getNow().minusHours(3));
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new JudgmentCreatedEvent(new Judgment(event.getJudgment().getReference(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForSubject() {
        time.set(time.getNow().minusHours(3));
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new JudgmentCreatedEvent(new Judgment(event.getJudgment().getReference(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReference(event.getJudgment().getReference(), Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(2));
    }

    @Test
    public void recordStatisticsForSteam() {
        final JudgmentCreatedEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        //assertThat(getStatisticsRepository().forReference(Repositories.references().getSteam()).getGood(), is(1));
    }

    private JudgmentCreatedEvent getGoodJudgmentEvent() {
        final JudgmentCreatedEvent event = new JudgmentCreatedEvent(TestFactories.judgments().newGoodJudgment());
        return event;
    }

    private JudgmentCreatedEvent getBadJudgmentEvent() {
        final JudgmentCreatedEvent event = new JudgmentCreatedEvent(TestFactories.judgments().newBadJudgment());
        return event;
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    private StatisticsFactory statisticsFactory;
}
