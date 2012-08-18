package com.steambeat.domain.reference;

import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.*;

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
    public void canManageReferences() {
        final Concept concept = TestFactories.concepts().newConcept();
        concept.addIfAbsent(TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr")));
        concept.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));
        concept.addIfAbsent(TestFactories.keywords().newKeyword("de", SteambeatLanguage.forString("de")));
        final ConceptTranslatedEvent event = new ConceptTranslatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        final List<Reference> references = Repositories.references().getAll();
        assertThat(references.get(0), is(references.get(1)));
        assertThat(references.get(0), is(references.get(2)));
    }

    @Test
    public void newReferenceIsOldest() {
        final Concept concept = TestFactories.concepts().newConcept();
        final Keyword oldestKeyword = TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr"));
        concept.addIfAbsent(oldestKeyword);
        time.waitDays(1);
        concept.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));
        concept.addIfAbsent(TestFactories.keywords().newKeyword("de", SteambeatLanguage.forString("de")));
        final ConceptTranslatedEvent event = new ConceptTranslatedEvent(concept);

        DomainEventBus.INSTANCE.post(event);

        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.get(0).getReferenceId(), is(oldestKeyword.getReferenceId()));
        assertThat(keywords.get(1).getReferenceId(), is(oldestKeyword.getReferenceId()));
        assertThat(keywords.get(2).getReferenceId(), is(oldestKeyword.getReferenceId()));
    }

    @Test
    public void otherReferencesAreInactives() {
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
    public void allOpinionsAreNowOnTheOldestReference() {

    }

    @Test
    public void allIllustrationsAreNowOnTheOldestReference() {

    }

    @Test
    public void allRelationsAreNowOnTheOldestReference() {

    }
}
