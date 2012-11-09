package com.feelhub.domain.world;

import com.feelhub.application.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsWorldListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        worldListener = new WorldListener(new FakeSessionProvider(), new KeywordService(new TopicService(new TopicFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())));
    }

    @Test
    public void addPostEventForWorldStatisticsOnSentimentEvent() {
        bus.capture(WorldStatisticsEvent.class);
        final Keyword world = TestFactories.keywords().newKeyword("", FeelhubLanguage.none());
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent, notNullValue());
        assertThat(worldStatisticsEvent.getSentiment(), notNullValue());
        assertThat(worldStatisticsEvent.getSentiment().getTopicId(), is(world.getTopicId()));
        assertThat(worldStatisticsEvent.getSentiment().getSentimentValue(), is(sentiment.getSentimentValue()));
    }

    @Test
    public void canCreateWorldIfNotPresent() {
        bus.capture(WorldStatisticsEvent.class);
        final Sentiment sentiment = TestFactories.sentiments().newSentiment();
        final SentimentStatisticsEvent sentimentStatisticsEvent = new SentimentStatisticsEvent(sentiment);

        DomainEventBus.INSTANCE.post(sentimentStatisticsEvent);

        final Keyword worldKeyword = Repositories.keywords().forValueAndLanguage("", FeelhubLanguage.none());
        assertThat(worldKeyword, notNullValue());
        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent.getSentiment().getTopicId(), is(worldKeyword.getTopicId()));
    }

    private WorldListener worldListener;
}
