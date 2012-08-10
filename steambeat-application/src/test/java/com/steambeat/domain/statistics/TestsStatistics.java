package com.steambeat.domain.statistics;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsStatistics {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canCreateFromSubject() {
        final Topic topic = TestFactories.topics().newTopic();

        final Statistics statistics = new Statistics(topic, Granularity.hour, new DateTime());

        assertThat(statistics.getTopicId(), is(topic.getId()));
    }
}
