package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.steam.SteamStatisticsEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.*;
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
        statisticsFactory = new StatisticsFactory(new FakeSessionProvider());
    }

    @Test
    public void canRecordGoodJudgment() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId()).get(0).getGood(), is(1));
    }

    @Test
    public void canRecordBadJudgment() {
        final JudgmentStatisticsEvent event = getBadJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId()).get(0).getBad(), is(1));
    }

    @Test
    public void canCreateFromEvent() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId()).get(0).getGood(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        time.waitDays(1);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGood(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2OpinionsForYear() {
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();
        DomainEventBus.INSTANCE.post(event);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(),
                Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordOpinionForLastYear() {
        time.set(time.getNow().minusYears(3));
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();

        time.set(time.getNow().plusYears(3));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(),
                Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canRecordOpinionsForLastMonth() {
        time.set(time.getNow().minusMonths(3));
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusMonths(5));
        statisticsFactory.handle(new JudgmentStatisticsEvent(new Judgment(event.getJudgment().getReferenceId(), Feeling.good)));

        time.set(time.getNow().plusMonths(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastDay() {
        time.set(time.getNow().minusDays(3));
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusDays(5));
        statisticsFactory.handle(new JudgmentStatisticsEvent(new Judgment(event.getJudgment().getReferenceId(), Feeling.good)));

        time.set(time.getNow().plusDays(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordOpinionsForLastHour() {
        time.set(time.getNow().minusHours(3));
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new JudgmentStatisticsEvent(new Judgment(event.getJudgment().getReferenceId(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForSubject() {
        time.set(time.getNow().minusHours(3));
        final JudgmentStatisticsEvent event = getGoodJudgmentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new JudgmentStatisticsEvent(new Judgment(event.getJudgment().getReferenceId(), Feeling.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forReferenceId(event.getJudgment().getReferenceId(), Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(2));
    }

    @Test
    public void canRecordStatisticsForSteam() {
        final Keyword steamKeyword = TestFactories.keywords().newKeyword("", FeelhubLanguage.none());
        final Judgment judgment = new Judgment(steamKeyword.getReferenceId(), Feeling.good);
        final SteamStatisticsEvent steamStatisticsEvent = new SteamStatisticsEvent(judgment);

        statisticsFactory.handle(steamStatisticsEvent);

        final List<Statistics> statisticsList = getStatisticsRepository().forReferenceId(steamKeyword.getReferenceId());
        assertThat(statisticsList.size(), is(5));
    }

    private JudgmentStatisticsEvent getGoodJudgmentEvent() {
        final JudgmentStatisticsEvent event = new JudgmentStatisticsEvent(TestFactories.judgments().newGoodJudgment());
        return event;
    }

    private JudgmentStatisticsEvent getBadJudgmentEvent() {
        final JudgmentStatisticsEvent event = new JudgmentStatisticsEvent(TestFactories.judgments().newBadJudgment());
        return event;
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    private StatisticsFactory statisticsFactory;
}
