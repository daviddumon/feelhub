package com.steambeat.domain.uri;

import com.steambeat.application.*;
import com.steambeat.domain.concept.ConceptEvent;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        bus.capture(CompleteUriEvent.class);
        final FakeUriResolver uriPathResolver = new FakeUriResolver();
        canonicalUri = "http://www.canonical.com";
        uriPathResolver.thatFind(canonicalUri);
        exceptionUri = "http://www.exception";
        uriPathResolver.ThatThrow(exceptionUri);
        new UriManager(new FakeSessionProvider(), uriPathResolver, new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Test
    public void postAPathCreatedEventOnSuccess() {
        final UriEvent uriEvent = TestFactories.events().newUriEvent("http://www.test.com");

        DomainEventBus.INSTANCE.post(uriEvent);

        final CompleteUriEvent completeUriEvent = bus.lastEvent(CompleteUriEvent.class);
        assertThat(completeUriEvent, notNullValue());
    }

    @Test
    public void addKeywordsToCompleteUriEvent() {
        final UriEvent uriEvent = TestFactories.events().newUriEvent("http://www.test.com");

        DomainEventBus.INSTANCE.post(uriEvent);

        final CompleteUriEvent completeUriEvent = bus.lastEvent(CompleteUriEvent.class);
        final CopyOnWriteArrayList<Keyword> keywords = completeUriEvent.getKeywords();
        assertThat(keywords.size(), is(4));
        assertThat(keywords.get(0).getValue(), is("http://www.test.com"));
        assertThat(keywords.get(1).getValue(), is("www.test.com"));
        assertThat(keywords.get(2).getValue(), is(canonicalUri));
        assertThat(keywords.get(3).getValue(), is(canonicalUri.replaceFirst("http://", "")));
    }

    @Test
    public void addNewKeywordsToRepository() {
        final UriEvent uriEvent = TestFactories.events().newUriEvent("http://www.test.com");

        DomainEventBus.INSTANCE.post(uriEvent);

        assertThat(Repositories.keywords().getAll().size(), is(4));
    }

    @Test
    public void useExistingKeyword() {
        TestFactories.keywords().newKeyword(canonicalUri, SteambeatLanguage.none());
        final UriEvent uriEvent = TestFactories.events().newUriEvent("http://www.test.com");

        DomainEventBus.INSTANCE.post(uriEvent);

        assertThat(Repositories.keywords().getAll().size(), is(4));
    }

    @Test
    public void postAConceptCreatedEventOnError() {
        bus.capture(ConceptEvent.class);
        final UriEvent uriEvent = TestFactories.events().newUriEvent(exceptionUri);

        DomainEventBus.INSTANCE.post(uriEvent);

        final ConceptEvent conceptEvent = bus.lastEvent(ConceptEvent.class);
        assertThat(conceptEvent, notNullValue());
        assertThat(conceptEvent.getKeywords().size(), is(1));
    }

    private String canonicalUri;
    private String exceptionUri;
}
