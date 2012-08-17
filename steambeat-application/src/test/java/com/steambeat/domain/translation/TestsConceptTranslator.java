package com.steambeat.domain.translation;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
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
        new FakeConceptTranslator();
    }

    @Test
    public void translateIfNotEnglish() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr"));
        final Concept concept = TestFactories.concepts().newConcept(keyword);
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getConcept().getKeywords().size(), is(2));
    }

    @Test
    public void translateAllKeywords() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr"));
        final Concept concept = TestFactories.concepts().newConcept(keyword);
        concept.addIfAbsent(TestFactories.keywords().newKeyword("another", SteambeatLanguage.forString("fr")));
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getConcept().getKeywords().size(), is(4));
    }

    @Test
    public void doNotCreateSameKeywordTwice() {
        final Concept concept = TestFactories.concepts().newConcept();
        concept.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        concept.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("de")));
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getConcept().getKeywords().size(), is(3));
    }

    @Test
    public void lookUpExistingTranslation() {
        final Concept concept = TestFactories.concepts().newConcept();
        concept.addIfAbsent(TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr")));
        final Keyword reference = TestFactories.keywords().newKeyword("valueenglish", SteambeatLanguage.reference());
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertTrue(event.getConcept().getKeywords().contains(reference));
    }

    @Test
    public void doNotTranslateIfLanguageNone() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.none());
        final Concept concept = TestFactories.concepts().newConcept(keyword);
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getConcept().getKeywords().size(), is(1));
    }

    @Test
    public void doNotTranslateIfLanguageReference() {
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.reference());
        final Concept concept = TestFactories.concepts().newConcept(keyword);
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        assertThat(event.getConcept().getKeywords().size(), is(1));
    }

    @Test
    public void spreadEventOnceTranslationDone() {
        bus.capture(TranslationDoneEvent.class);
        final Keyword keyword = TestFactories.keywords().newKeyword("value", SteambeatLanguage.forString("fr"));
        final Concept concept = TestFactories.concepts().newConcept(keyword);
        final ConceptCreatedEvent event = new ConceptCreatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        final TranslationDoneEvent translationDoneEvent = bus.lastEvent(TranslationDoneEvent.class);
        assertThat(translationDoneEvent, notNullValue());
        assertThat(translationDoneEvent.getDate(), is(time.getNow()));
        assertThat(translationDoneEvent.getConcept(), is(concept));
    }
}
