package com.steambeat.domain.subject;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.subject.concept.ConceptCreatedEvent;
import com.steambeat.domain.subject.uri.UriCreatedEvent;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
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
        subjectIdentifier = new SubjectIdentifier();
    }

    @Test
    public void canPostUriCreatedEvent() {
        bus.capture(UriCreatedEvent.class);
        final KeywordCreatedEvent event = getEventForUri("http://www.google.com");

        subjectIdentifier.handle(event);

        final UriCreatedEvent uriCreatedEvent = bus.lastEvent(UriCreatedEvent.class);
        assertThat(uriCreatedEvent, notNullValue());
    }

    @Test
        public void canPostConceptCreatedEvent() {
            bus.capture(ConceptCreatedEvent.class);
            final KeywordCreatedEvent event = getEventForUri("http://www.google.com");

            subjectIdentifier.handle(event);

            final UriCreatedEvent uriCreatedEvent = bus.lastEvent(UriCreatedEvent.class);
            assertThat(uriCreatedEvent, notNullValue());
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

    private KeywordCreatedEvent getEventForUri(final String value) {
        final Keyword keyword = TestFactories.keywords().newKeyword(value, Language.none());
        return new KeywordCreatedEvent(keyword);
    }

    private SubjectIdentifier subjectIdentifier;
}
