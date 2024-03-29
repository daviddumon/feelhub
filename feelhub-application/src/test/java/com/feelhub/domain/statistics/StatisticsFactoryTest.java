package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.world.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.*;
import org.joda.time.Interval;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class StatisticsFactoryTest {

    @Rule
    public WithFakeRepositories repos = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        statisticsFactory = new StatisticsFactory();
    }

    @Test
    public void canRecordHappyFeeling() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        assertThat(getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId()).get(0).getHappy()).isEqualTo(1);
    }

    @Test
    public void canRecordSadFeeling() {
        final FeelingCreatedEvent feelingEvent = getSadFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        assertThat(getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId()).get(0).getSad()).isEqualTo(1);
    }

    @Test
    public void canRecordForHour() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        time.waitDays(1);

        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.hour, new Interval(time.getNow().minusDays(2), time.getNow()));
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getGranularity()).isEqualTo(Granularity.hour);
        assertThat(statistics.get(0).getHappy()).isEqualTo(1);
    }

    @Test
    public void canRecordForDay() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.day, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getGranularity()).isEqualTo(Granularity.day);
    }

    @Test
    public void canRecordForMonth() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.month, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getGranularity()).isEqualTo(Granularity.month);
    }

    @Test
    public void canRecordForYear() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getGranularity()).isEqualTo(Granularity.year);
    }

    @Test
    public void canRecord2FeelingsForYear() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        DomainEventBus.INSTANCE.post(feelingEvent);

        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(),
                Granularity.year, new Interval(time.getNow().minus(1), time.getNow().plus(1)));
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getGranularity()).isEqualTo(Granularity.year);
    }

    @Test
    public void canRecordFeelingForLastYear() {
        time.set(time.getNow().minusYears(3));
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        time.set(time.getNow().plusYears(3));
        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(),
                Granularity.year, new Interval(time.getNow().minusYears(4), time.getNow().minusYears(2)));
        assertThat(statistics.size()).isEqualTo(1);
    }

    @Test
    public void canRecordFeelingsForLastMonth() {
        time.set(time.getNow().minusMonths(3));
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        time.set(time.getNow().minusMonths(5));
        statisticsFactory.handle(new FeelingCreatedEvent(feelingEvent.getFeeling()));

        time.set(time.getNow().plusMonths(8));
        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.month, new Interval(time.getNow().minusMonths(12), time.getNow()));
        assertThat(statistics.size()).isEqualTo(2);
    }

    @Test
    public void canRecordFeelingsForLastDay() {
        time.set(time.getNow().minusDays(3));
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        time.set(time.getNow().minusDays(5));
        statisticsFactory.handle(new FeelingCreatedEvent(feelingEvent.getFeeling()));

        time.set(time.getNow().plusDays(8));
        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.day, new Interval(time.getNow().minusDays(12), time.getNow()));
        assertThat(statistics.size()).isEqualTo(2);
    }

    @Test
    public void canRecordFeelingsForLastHour() {
        time.set(time.getNow().minusHours(3));
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new FeelingCreatedEvent(feelingEvent.getFeeling()));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.hour, new Interval(time.getNow().minusHours(12), time.getNow()));
        assertThat(statistics.size()).isEqualTo(2);
    }

    @Test
    public void canFetchStatisticsWithGranularityAllForTopic() {
        time.set(time.getNow().minusHours(3));
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();
        time.set(time.getNow().minusHours(5));
        statisticsFactory.handle(new FeelingCreatedEvent(feelingEvent.getFeeling()));

        time.set(time.getNow().plusHours(8));
        statisticsFactory.handle(feelingEvent);

        final List<Statistics> statistics = getStatisticsRepository().forTopicId(feelingEvent.getFeeling().getTopicId(), Granularity.all);
        assertThat(statistics.size()).isEqualTo(1);
        assertThat(statistics.get(0).getHappy()).isEqualTo(2);
    }

    @Test
    public void canRecordStatisticsForWorld() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();
        final WorldStatisticsEvent worldStatisticsEvent = new WorldStatisticsEvent(TestFactories.feelings().happyFeeling(worldTopic));

        statisticsFactory.handle(worldStatisticsEvent);

        final List<Statistics> statisticsList = getStatisticsRepository().forTopicId(worldTopic.getId());
        assertThat(statisticsList.size()).isEqualTo(5);
    }

    @Test
    public void updatesFeelingCountOnTopic() {
        final FeelingCreatedEvent feelingEvent = getHappyFeelingEvent();

        statisticsFactory.handle(feelingEvent);

        final List<Topic> topics = Repositories.topics().getAll();
        assertThat(topics.get(0).getHappyFeelingCount()).isEqualTo(1);
    }

    private FeelingCreatedEvent getHappyFeelingEvent() {
        final Feeling feeling = TestFactories.feelings().happyFeeling();
        final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(feeling);
        return feelingCreatedEvent;
    }

    private FeelingCreatedEvent getSadFeelingEvent() {
        final Feeling feeling = TestFactories.feelings().sadFeeling();
        final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(feeling);
        return feelingCreatedEvent;
    }

    private FakeStatisticsRepository getStatisticsRepository() {
        return (FakeStatisticsRepository) Repositories.statistics();
    }

    private StatisticsFactory statisticsFactory;
}
