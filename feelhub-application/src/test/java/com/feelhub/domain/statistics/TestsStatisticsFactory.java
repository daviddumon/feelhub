package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.world.*;
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
        statisticsFactory = new StatisticsFactory();
    }

    @Test
    public void canRecordGoodSentiment() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forTopicId(event.getSentiment().getTopicId()).get(0).getGood(), is(1));
    }

    @Test
    public void canRecordBadSentiment() {
        final SentimentStatisticsEvent event = getBadSentimentEvent();

        statisticsFactory.handle(event);

        assertThat(getStatisticsRepository().forTopicId(event.getSentiment().getTopicId()).get(0).getBad(), is(1));
    }

    @Test
    public void canRecordForHour() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        time.waitDays(1);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.hour));
        assertThat(statistics.get(0).getGood(), is(1));
    }

    @Test
    public void canRecordForDay() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.day));
    }

    @Test
    public void canRecordForMonth() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.month));
    }

    @Test
    public void canRecordForYear() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecord2SentimentsForYear() {
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        DomainEventBus.INSTANCE.post(event);

        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(),
                Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGranularity(), is(Granularity.year));
    }

    @Test
    public void canRecordSentimentForLastYear() {
        time.set(time.getNow().minusYears(3));
        final SentimentStatisticsEvent event = getGoodSentimentEvent();

        time.set(time.getNow().plusYears(3));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(),
                Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size(), is(1));
    }

    @Test
    public void canRecordSentimentsForLastMonth() {
        time.set(time.getNow().minusMonths(3));
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        time.set(time.getNow().minusMonths(5));
        statisticsFactory.handle(new SentimentStatisticsEvent(new Sentiment(event.getSentiment().getTopicId(), SentimentValue.good)));

        time.set(time.getNow().plusMonths(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordSentimentsForLastDay() {
        time.set(time.getNow().minusDays(3));
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        time.set(time.getNow().minusDays(5));
        statisticsFactory.handle(new SentimentStatisticsEvent(new Sentiment(event.getSentiment().getTopicId(), SentimentValue.good)));

        time.set(time.getNow().plusDays(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canRecordSentimentsForLastHour() {
        time.set(time.getNow().minusHours(3));
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new SentimentStatisticsEvent(new Sentiment(event.getSentiment().getTopicId(), SentimentValue.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size(), is(2));
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForTopic() {
        time.set(time.getNow().minusHours(3));
        final SentimentStatisticsEvent event = getGoodSentimentEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new SentimentStatisticsEvent(new Sentiment(event.getSentiment().getTopicId(), SentimentValue.good)));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(event);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(event.getSentiment().getTopicId(), Granularity.all);
        assertThat(statistics.size(), is(1));
        assertThat(statistics.get(0).getGood(), is(2));
    }

    @Test
    public void canRecordStatisticsForWorld() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();
        final Sentiment sentiment = new Sentiment(worldTopic.getId(), SentimentValue.good);
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(sentiment);

        statisticsFactory.handle(worldStatisticsEvent);

        final List<Statistics> statisticsList = getStatisticsRepository().forTopicId(worldTopic.getId());
        assertThat(statisticsList.size(), is(5));
    }

    private SentimentStatisticsEvent getGoodSentimentEvent() {
        final SentimentStatisticsEvent event = new SentimentStatisticsEvent(TestFactories.sentiments().newGoodSentiment());
        return event;
    }

    private SentimentStatisticsEvent getBadSentimentEvent() {
        final SentimentStatisticsEvent event = new SentimentStatisticsEvent(TestFactories.sentiments().newBadSentiment());
        return event;
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    private StatisticsFactory statisticsFactory;
}
