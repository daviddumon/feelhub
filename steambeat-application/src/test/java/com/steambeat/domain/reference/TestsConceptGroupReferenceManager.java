package com.steambeat.domain.reference;

import com.steambeat.domain.concept.ConceptGroupTranslatedEvent;
import com.steambeat.domain.eventbus.*;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptGroupReferenceManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        new ConceptGroupReferenceManager(new FakeSessionProvider());
    }

    @Test
    public void postAConceptGroupReferencesChangedEvent() {
        bus.capture(ConceptGroupReferencesChangedEvent.class);
        final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent = TestFactories.events().newConceptGroupTranslatedEvent();

        DomainEventBus.INSTANCE.post(conceptGroupTranslatedEvent);

        final ConceptGroupReferencesChangedEvent conceptGroupReferencesChangedEvent = bus.lastEvent(ConceptGroupReferencesChangedEvent.class);
        assertThat(conceptGroupReferencesChangedEvent, notNullValue());
    }
}
