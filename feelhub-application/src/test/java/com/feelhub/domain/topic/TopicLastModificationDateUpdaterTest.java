package com.feelhub.domain.topic;


import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.feeling.FeelingCreatedEvent;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

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
        Feeling feeling = TestFactories.feelings().newFeeling(realTopic.getId(), "text");
        FeelingCreatedEvent event = new FeelingCreatedEvent(feeling.getId(), feeling.getUserId());

        new TopicLastModificationDateUpdater().onFeelingCreated(event);

        assertThat(realTopic.getLastModificationDate()).isEqualTo(new DateTime(2012, 12, 1, 0, 0));
    }
}
