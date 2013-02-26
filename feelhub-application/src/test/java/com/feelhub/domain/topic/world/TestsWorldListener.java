package com.feelhub.domain.topic.world;

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
            }
        });
        injector.getInstance(WorldListener.class);
    }

    @Test
    public void addPostEventForWorldStatisticsOnSentimentEvent() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment()).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment().getTopicId()).isEqualTo(worldTopic.getId());
        assertThat(worldStatisticsEvent.getSentiment().getSentimentValue()).isEqualTo(sentiment.getSentimentValue());
    }

    @Test
    public void canCreateWorldIfNotPresent() {
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final WorldTopic worldTopic = Repositories.topics().getWorldTopic();
        assertThat(worldTopic).isNotNull();
        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent.getSentiment().getTopicId()).isEqualTo(worldTopic.getId());
    }
}
