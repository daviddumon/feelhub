package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatistics {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canCreateFromTopic() {
        final Topic topic = TestFactories.topics().newTopic();

        final Statistics statistics = new Statistics(topic.getId(), Granularity.hour, new DateTime());

        assertThat(statistics.getTopicId(), is(topic.getId()));
    }
}
