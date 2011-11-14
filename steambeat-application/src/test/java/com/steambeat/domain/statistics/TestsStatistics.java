package com.steambeat.domain.statistics;

import com.steambeat.test.WithDomainEvent;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsStatistics {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canCreateFromFeed() {
        final Statistics statistics = new Statistics(TestFactories.feeds().newFeed("http://uneuri"), Granularity.hour, new DateTime());

        assertThat((String) statistics.getSubjectId(), is("http://uneuri"));
    }
}
