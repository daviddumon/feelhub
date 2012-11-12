package com.feelhub.domain.keyword.world;

import com.feelhub.application.WorldService;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.KeywordFactory;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsWorldListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        worldListener = new WorldListener(new FakeSessionProvider(), new WorldService(new TopicFactory(), new KeywordFactory()));
    }

    @Test
    public void addPostEventForWorldStatisticsOnSentimentEvent() {
        bus.capture(WorldStatisticsEvent.class);
        final World world = TestFactories.keywords().newWorld();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment()).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment().getTopicId()).isEqualTo(world.getTopicId());
        assertThat(worldStatisticsEvent.getSentiment().getSentimentValue()).isEqualTo(sentiment.getSentimentValue());
    }

    @Test
    public void canCreateWorldIfNotPresent() {
        bus.capture(WorldStatisticsEvent.class);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final World world = Repositories.keywords().world();
        assertThat(world).isNotNull();
        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent.getSentiment().getTopicId()).isEqualTo(world.getTopicId());
    }

    private WorldListener worldListener;
}
