package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class FeelingFactoryTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        feelingFactory = new FeelingFactory();
    }

    @Test
    public void canCreateAFeeling() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");

        final Feeling feeling = feelingFactory.createFeeling(user.getId(), realTopic.getId());

        assertThat(feeling.getId()).isNotNull();
        assertThat(feeling.getUserId()).isEqualTo(user.getId());
        assertThat(feeling.getTopicId()).isEqualTo(realTopic.getCurrentId());
    }

    @Test
    public void canPostAnEvent() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final Feeling feeling = feelingFactory.createFeeling(user.getId(), realTopic.getCurrentId());

        final FeelingCreatedEvent feelingEvent = events.lastEvent(FeelingCreatedEvent.class);
        assertThat(feelingEvent).isNotNull();
        assertThat(feelingEvent.getFeeling().getId()).isEqualTo(feeling.getId());
    }

    @Test
    public void canPostFirstFeelingEvent() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final Feeling feeling = feelingFactory.createFeeling(user.getId(), realTopic.getCurrentId());

        final FirstFeelingCreatedEvent firstFeelingEvent = events.lastEvent(FirstFeelingCreatedEvent.class);
        assertThat(firstFeelingEvent).isNotNull();
        assertThat(firstFeelingEvent.getFeeling().getId()).isEqualTo(feeling.getId());
    }

    @Test
    public void onlyPostFirstFeelingEventForFirstFeeling() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.increasesFeelingCount(TestFactories.feelings().happyFeeling(realTopic));

        final Feeling feeling = feelingFactory.createFeeling(user.getId(), realTopic.getCurrentId());

        final FirstFeelingCreatedEvent firstFeelingEvent = events.lastEvent(FirstFeelingCreatedEvent.class);
        assertThat(firstFeelingEvent).isNull();
    }

    private FeelingFactory feelingFactory;
}
