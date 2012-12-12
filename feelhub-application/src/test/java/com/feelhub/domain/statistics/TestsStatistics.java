package com.feelhub.domain.statistics;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.real.RealTopic;
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
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final Statistics statistics = new Statistics(realTopic.getId(), Granularity.hour, new DateTime());

        assertThat(statistics.getTopicId(), is(realTopic.getId()));
    }
}
