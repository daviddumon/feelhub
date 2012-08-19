package com.steambeat.domain.reference;

import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReferenceManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        new ReferenceManager();
    }

    @Test
    public void setOnlyTheOldestReferenceAsActive() {
        final Concept concept = TestFactories.concepts().newConcept();
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        concept.addIfAbsent(goodKeyword);
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en"));
        concept.addIfAbsent(badKeyword);
        final ConceptTranslatedEvent event = new ConceptTranslatedEvent(concept);
        UUID oldId = badKeyword.getReferenceId();
        UUID goodId = goodKeyword.getReferenceId();

        DomainEventBus.INSTANCE.post(event);

        final Reference badReference = Repositories.references().get(oldId);
        assertThat(badReference, notNullValue());
        assertThat(badReference.isActive(), is(false));
        final Reference goodReference = Repositories.references().get(goodId);
        assertThat(goodReference, notNullValue());
        assertThat(goodReference.isActive(), is(true));
    }

    @Test
    public void postAReferencesChangedEvent() {
        bus.capture(ReferencesChangedEvent.class);
        final Concept concept = TestFactories.concepts().newConcept();
        concept.addIfAbsent(TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr")));
        concept.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));
        final ConceptTranslatedEvent event = new ConceptTranslatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        final ReferencesChangedEvent referencesChangedEvent = bus.lastEvent(ReferencesChangedEvent.class);
        assertThat(referencesChangedEvent, notNullValue());
        assertThat(referencesChangedEvent.getReferenceIds().size(), is(1));
        assertThat(referencesChangedEvent.getNewReferenceId(), notNullValue());
    }
}
