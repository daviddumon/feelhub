package com.steambeat.domain.concept;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptGroupTranslator {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        fakeConceptGroupTranslator = new FakeConceptGroupTranslator();
    }

    @Test
    public void canHandleConceptGroupEvent() {
        bus.capture(ConceptGroupTranslatedEvent.class);
        final ConceptEvent event1 = TestFactories.events().newConceptEvent();
        event1.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        final ConceptEvent event2 = TestFactories.events().newConceptEvent();
        event2.addIfAbsent(TestFactories.keywords().newKeyword("another", SteambeatLanguage.forString("fr")));
        final ConceptGroupEvent conceptGroupEvent = new ConceptGroupEvent();
        conceptGroupEvent.addIfAbsent(event1);
        conceptGroupEvent.addIfAbsent(event2);

        DomainEventBus.INSTANCE.post(conceptGroupEvent);

        assertThat(Repositories.keywords().getAll().size(), is(4));
    }

    @Test
    public void postConceptGroupTranslatedEvent() {
        bus.capture(ConceptGroupTranslatedEvent.class);
        final ConceptEvent event1 = TestFactories.events().newConceptEvent();
        event1.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        final ConceptEvent event2 = TestFactories.events().newConceptEvent();
        event2.addIfAbsent(TestFactories.keywords().newKeyword("another", SteambeatLanguage.forString("fr")));
        final ConceptGroupEvent conceptGroupEvent = new ConceptGroupEvent();
        conceptGroupEvent.addIfAbsent(event1);
        conceptGroupEvent.addIfAbsent(event2);

        DomainEventBus.INSTANCE.post(conceptGroupEvent);

        final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent = bus.lastEvent(ConceptGroupTranslatedEvent.class);
        assertThat(conceptGroupTranslatedEvent, notNullValue());
    }

    private FakeConceptGroupTranslator fakeConceptGroupTranslator;
}
