package com.feelhub.domain.topic;


import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.joda.time.DateTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TopicLastModificationDateUpdaterTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent domainEvent = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canUpdateLastModificationDate() {
        time.set(new DateTime(2012, 12, 1, 0, 0));
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.setLastModificationDate(new DateTime(1, 1, 1, 0, 0));
        final Feeling feeling = TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        final FeelingCreatedEvent event = new FeelingCreatedEvent(feeling);

        new TopicLastModificationDateUpdater().onFeelingCreated(event);

        assertThat(realTopic.getLastModificationDate()).isEqualTo(new DateTime(2012, 12, 1, 0, 0));
    }
}
