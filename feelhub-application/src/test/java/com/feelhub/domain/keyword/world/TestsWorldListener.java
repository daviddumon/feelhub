package com.feelhub.domain.keyword.world;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsWorldListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        injector.getInstance(WorldListener.class);
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
}
