package com.steambeat.domain;

import com.steambeat.domain.concept.ConceptCreatedEvent;
import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.uri.UriCreatedEvent;
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
    public void canPostUriCreatedEvent() {
        bus.capture(UriCreatedEvent.class);
        final KeywordCreatedEvent event = getEventFor("http://www.google.com");

        subjectIdentifier.handle(event);

        final UriCreatedEvent uriCreatedEvent = bus.lastEvent(UriCreatedEvent.class);
        assertThat(uriCreatedEvent, notNullValue());
    }

    @Test
    public void canPostConceptCreatedEvent() {
        bus.capture(ConceptCreatedEvent.class);
        final KeywordCreatedEvent event = getEventFor("google");

        subjectIdentifier.handle(event);

        final ConceptCreatedEvent conceptCreatedEvent = bus.lastEvent(ConceptCreatedEvent.class);
        assertThat(conceptCreatedEvent, notNullValue());
        assertThat(conceptCreatedEvent.getConcept(), notNullValue());
        assertThat(conceptCreatedEvent.getConcept().getKeywords(), notNullValue());
        assertThat(conceptCreatedEvent.getConcept().getKeywords().size(), is(1));
    }

    @Test
    public void canIdentifyUris() {
        assertTrue(SubjectIdentifier.isUri("http://www.google.com"));
        assertTrue(SubjectIdentifier.isUri("HTTP://www.google.com"));
        assertTrue(SubjectIdentifier.isUri("http://www.sub.google.com"));
        assertTrue(SubjectIdentifier.isUri("http://sub-test.google.com"));
        assertTrue(SubjectIdentifier.isUri("http://www.google.com/"));
        assertTrue(SubjectIdentifier.isUri("https://www.google.com"));
        assertTrue(SubjectIdentifier.isUri("https://www.google.com/"));
        assertTrue(SubjectIdentifier.isUri("www.google.com"));
        assertTrue(SubjectIdentifier.isUri("www.google.com/"));
        assertTrue(SubjectIdentifier.isUri("google.com"));
        assertTrue(SubjectIdentifier.isUri("google.com/"));
        assertTrue(SubjectIdentifier.isUri("http://google.com"));
        assertTrue(SubjectIdentifier.isUri("http://google.com/"));
        assertTrue(SubjectIdentifier.isUri("http://google.com/bin/#"));
        assertTrue(SubjectIdentifier.isUri("http://google.com/bin/#arf?id=true"));
        assertTrue(SubjectIdentifier.isUri("yala.fr"));
        assertTrue(SubjectIdentifier.isUri("yala.fr/"));
        assertTrue(SubjectIdentifier.isUri("www.%C3%A9l%C3%A9phant.com"));

        assertFalse(SubjectIdentifier.isUri("notanuri"));
        assertFalse(SubjectIdentifier.isUri("httpnotanuri"));
        assertFalse(SubjectIdentifier.isUri("http:notanuri"));
        assertFalse(SubjectIdentifier.isUri("http:/notanuri"));
        assertFalse(SubjectIdentifier.isUri("http://notanuri"));
        assertFalse(SubjectIdentifier.isUri("http://notanuri/"));
        assertFalse(SubjectIdentifier.isUri("http://notanuri/zala#lol"));
        assertFalse(SubjectIdentifier.isUri("notanuri.comm"));
        assertFalse(SubjectIdentifier.isUri(".com"));
        assertFalse(SubjectIdentifier.isUri(".fr"));
        assertFalse(SubjectIdentifier.isUri(".c"));
        assertFalse(SubjectIdentifier.isUri(".come"));
        assertFalse(SubjectIdentifier.isUri("ftp://www.google.com"));
        assertFalse(SubjectIdentifier.isUri("www.%C3%A9l%C3%A9phant."));
    }

    private KeywordCreatedEvent getEventFor(final String value) {
        final Keyword keyword = TestFactories.keywords().newKeyword(value, SteambeatLanguage.none());
        return new KeywordCreatedEvent(keyword);
    }

    private SubjectIdentifier subjectIdentifier;
}
