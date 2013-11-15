package com.feelhub.domain.topic.world;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

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
    public void addPostEventForWorldStatisticsOnFeelingCreatedEvent() {
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();
        final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(TestFactories.feelings().happyFeeling());

        worldListener.handle(feelingCreatedEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent).isNotNull();
        assertThat(worldStatisticsEvent.getFeeling()).isNotNull();
        assertThat(worldStatisticsEvent.getFeeling().getTopicId()).isEqualTo(worldTopic.getCurrentId());
        assertThat(worldStatisticsEvent.getFeeling().getFeelingValue()).isEqualTo(FeelingValue.happy);
    }

    @Test
    public void listenToFeelingCreatedEvent() {
        final FeelingCreatedEvent feelingCreatedEvent = new FeelingCreatedEvent(TestFactories.feelings().happyFeeling());

        DomainEventBus.INSTANCE.post(feelingCreatedEvent);

        final WorldTopic worldTopic = Repositories.topics().getWorldTopic();
        assertThat(worldTopic).isNotNull();
    }

    private WorldListener worldListener;
}
