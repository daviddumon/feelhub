package com.steambeat.domain.reference;

import com.steambeat.domain.concept.ConceptTranslatedEvent;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.*;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptReferenceManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        new ConceptReferenceManager(new FakeSessionProvider());
    }

    @Test
    public void setOnlyTheOldestReferenceAsActive() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en"));
        final ConceptTranslatedEvent event = TestFactories.events().newConceptTranslatedEvent();
        event.addIfAbsent(goodKeyword);
        event.addIfAbsent(badKeyword);
        final UUID oldId = badKeyword.getReferenceId();
        final UUID goodId = goodKeyword.getReferenceId();

        DomainEventBus.INSTANCE.post(event);

        final Reference badReference = Repositories.references().get(oldId);
        assertThat(badReference, notNullValue());
        assertThat(badReference.isActive(), is(false));
        final Reference goodReference = Repositories.references().get(goodId);
        assertThat(goodReference, notNullValue());
        assertThat(goodReference.isActive(), is(true));
    }

    @Test
    public void postAConceptReferencesChangedEvent() {
        bus.capture(ConceptReferencesChangedEvent.class);
        final ConceptTranslatedEvent event = TestFactories.events().newConceptTranslatedEvent();
        event.addIfAbsent(TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr")));
        event.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));

        DomainEventBus.INSTANCE.post(event);

        final ConceptReferencesChangedEvent conceptReferencesChangedEvent = bus.lastEvent(ConceptReferencesChangedEvent.class);
        assertThat(conceptReferencesChangedEvent, notNullValue());
        assertThat(conceptReferencesChangedEvent.getReferenceIds().size(), is(1));
        assertThat(conceptReferencesChangedEvent.getNewReferenceId(), notNullValue());
    }

    @Test
    public void oldReferenceKeepALinkToTheNewReference() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en"));
        final ConceptTranslatedEvent event = TestFactories.events().newConceptTranslatedEvent();
        event.addIfAbsent(goodKeyword);
        event.addIfAbsent(badKeyword);
        final UUID oldId = badKeyword.getReferenceId();
        final UUID goodId = goodKeyword.getReferenceId();

        DomainEventBus.INSTANCE.post(event);

        final Reference badReference = Repositories.references().get(oldId);
        assertThat(badReference.isActive(), is(false));
        assertThat(badReference.getCurrentReferenceId(), is(goodId));
    }
}
