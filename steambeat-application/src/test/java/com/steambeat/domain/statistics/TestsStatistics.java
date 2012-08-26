package com.steambeat.domain.statistics;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
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
    public void canCreateFromReference() {
        final Reference reference = TestFactories.references().newReference();

        final Statistics statistics = new Statistics(reference, Granularity.hour, new DateTime());

        assertThat(statistics.getReferenceId(), is(reference.getId()));
    }
}
