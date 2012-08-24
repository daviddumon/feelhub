package com.steambeat.domain.statistics;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsStatisticsManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        new StatisticsManager(new FakeSessionProvider());
    }

    @Test
    public void canChangeStatisticsReferencesForAConcept() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        TestFactories.statistics().newStatistics(ref1, Granularity.all);
        TestFactories.statistics().newStatistics(ref1, Granularity.day);
        TestFactories.statistics().newStatistics(ref1, Granularity.hour);
        TestFactories.statistics().newStatistics(ref2, Granularity.hour);
        TestFactories.statistics().newStatistics(ref2, Granularity.month);
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref2.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Statistics> statistics = Repositories.statistics().forReferenceId(ref1.getId());
        assertThat(statistics.size(), is(5));
    }
}
