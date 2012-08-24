package com.steambeat.domain;

import com.steambeat.domain.concept.ConceptEvent;
import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.uri.UriEvent;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSubjectIdentifier {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        subjectIdentifier = new SubjectIdentifier(new FakeSessionProvider());
    }

    @Test
    public void canPostUriEvent() {
        bus.capture(UriEvent.class);
        final KeywordCreatedEvent event = getEventFor("http://www.google.com");

        subjectIdentifier.handle(event);

        final UriEvent uriEvent = bus.lastEvent(UriEvent.class);
        assertThat(uriEvent, notNullValue());
        assertThat(uriEvent.getKeyword(), notNullValue());
    }

    @Test
    public void canPostConceptEvent() {
        bus.capture(ConceptEvent.class);
        final KeywordCreatedEvent event = getEventFor("google");

        subjectIdentifier.handle(event);

        final ConceptEvent conceptEvent = bus.lastEvent(ConceptEvent.class);
        assertThat(conceptEvent, notNullValue());
        assertThat(conceptEvent.getKeywords(), notNullValue());
        assertThat(conceptEvent.getKeywords().size(), is(1));
    }

    @Test
    public void canIdentifyUris() {
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://www.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("HTTP://www.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://www.sub.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://sub-test.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://www.google.com/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("https://www.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("https://www.google.com/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("www.google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("www.google.com/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("google.com/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://google.com")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://google.com/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://google.com/bin/#")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://google.com/bin/#arf?id=true")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("yala.fr")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("yala.fr/")));
        assertTrue(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("www.%C3%A9l%C3%A9phant.com")));

        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("notanuri")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("httpnotanuri")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http:notanuri")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http:/notanuri")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://notanuri")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://notanuri/")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("http://notanuri/zala#lol")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("notanuri.comm")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword(".com")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword(".fr")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword(".c")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword(".come")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("ftp://www.google.com")));
        assertFalse(SubjectIdentifier.isUri(TestFactories.keywords().newKeyword("www.%C3%A9l%C3%A9phant.")));
    }

    private KeywordCreatedEvent getEventFor(final String value) {
        final Keyword keyword = TestFactories.keywords().newKeyword(value, SteambeatLanguage.none());
        return new KeywordCreatedEvent(keyword);
    }

    private SubjectIdentifier subjectIdentifier;
}
