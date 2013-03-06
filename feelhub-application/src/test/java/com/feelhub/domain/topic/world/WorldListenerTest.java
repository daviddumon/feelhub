package com.feelhub.domain.topic.world;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.domain.feeling.SentimentAddedEvent;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class WorldListenerTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();


    @Before
    public void before() {
        worldListener = new WorldListener();
    }

    @Test
    public void canLookupWorld() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();

        final WorldTopic worldTopicFound = worldListener.lookUpOrCreateWorld();

        assertThat(worldTopicFound).isNotNull();
        assertThat(worldTopicFound).isEqualTo(worldTopic);
    }

    @Test
    public void canCreateWorldIfItDoesNotExists() {
        worldListener.lookUpOrCreateWorld();

        assertThat(Repositories.topics().getWorldTopic()).isNotNull();
    }

    @Test
    public void addPostEventForWorldStatisticsOnSentimentEvent() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentAddedEvent sentimentStatisticsEvent = new SentimentAddedEvent(sentiment);

        worldListener.handle(sentimentStatisticsEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment()).isNotNull();
        assertThat(worldStatisticsEvent.getSentiment().getTopicId()).isEqualTo(worldTopic.getId());
        assertThat(worldStatisticsEvent.getSentiment().getSentimentValue()).isEqualTo(sentiment.getSentimentValue());
    }

    @Test
    public void listenToSentimentAdded() {
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentAddedEvent sentimentStatisticsEvent = new SentimentAddedEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final WorldTopic worldTopic = Repositories.topics().getWorldTopic();
        assertThat(worldTopic).isNotNull();
    }

    private WorldListener worldListener;
}
