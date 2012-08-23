package com.steambeat.domain.concept;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConceptTranslator {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        fakeConceptTranslator = new FakeConceptTranslator();
    }

    @Test
    public void translateIfNotEnglish() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr"));
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(keyword);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getKeywords().size(), is(2));
    }

    @Test
    public void translateAllKeywords() {
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        event.addIfAbsent(TestFactories.keywords().newKeyword("another", SteambeatLanguage.forString("fr")));

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getKeywords().size(), is(4));
    }

    @Test
    public void doNotCreateSameKeywordTwice() {
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        event.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("de")));

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getKeywords().size(), is(3));
    }

    @Test
    public void lookUpExistingTranslation() {
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        final Keyword reference = TestFactories.keywords().newKeyword("valueenglish", SteambeatLanguage.reference());
        event.addIfAbsent(reference);

        DomainEventBus.INSTANCE.post(event);

        assertTrue(event.getKeywords().contains(reference));
    }

    @Test
    public void doNotTranslateIfLanguageNone() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.none());
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(keyword);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getKeywords().size(), is(1));
    }

    @Test
    public void doNotTranslateIfLanguageReference() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.reference());
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(keyword);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getKeywords().size(), is(1));
    }

    @Test
    public void spreadEventOnceTranslationDone() {
        bus.capture(ConceptTranslatedEvent.class);
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr"));
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(keyword);

        DomainEventBus.INSTANCE.post(event);

        final ConceptTranslatedEvent conceptTranslatedEvent = bus.lastEvent(ConceptTranslatedEvent.class);
        assertThat(conceptTranslatedEvent, notNullValue());
        assertThat(conceptTranslatedEvent.getDate(), is(time.getNow()));
        assertThat(conceptTranslatedEvent.getKeywords(), is(event.getKeywords()));
    }

    @Test
    public void spreadEventOnlyIfTranslated() {
        bus.capture(ConceptTranslatedEvent.class);
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.reference());
        final ConceptEvent event = TestFactories.events().newConceptEvent();
        event.addIfAbsent(keyword);

        fakeConceptTranslator.translate(event);

        final ConceptTranslatedEvent conceptTranslatedEvent = bus.lastEvent(ConceptTranslatedEvent.class);
        assertThat(conceptTranslatedEvent, nullValue());
    }

    private FakeConceptTranslator fakeConceptTranslator;
}
