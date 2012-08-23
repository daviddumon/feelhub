package com.steambeat.domain.uri;

import com.steambeat.application.*;
import com.steambeat.domain.concept.ConceptCreatedEvent;
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
        final FakeUriPathResolver uriPathResolver = new FakeUriPathResolver();
        canonicalUri = "http://www.canonical.com";
        uriPathResolver.thatFind(new Uri(canonicalUri));
        exceptionUri = "http://www.exception";
        uriPathResolver.ThatThrow(new Uri(exceptionUri));
        new UriManager(new FakeSessionProvider(), uriPathResolver, new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Test
    public void postAPathCreatedEventOnSuccess() {
        bus.capture(PathCreatedEvent.class);
        final UriCreatedEvent event = createEventFor(canonicalUri);

        DomainEventBus.INSTANCE.post(event);

        final PathCreatedEvent pathCreatedEvent = bus.lastEvent(PathCreatedEvent.class);
        assertThat(pathCreatedEvent, notNullValue());
    }
    //
    //@Test
    //public void addPathToKeywords() {
    //    final UriCreatedEvent event = createEventFor("http://liberation.fr");
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final CopyOnWriteArrayList<Keyword> keywords = event.getUri().getKeywords();
    //    assertThat(keywords.size(), is(2));
    //    assertThat(keywords.get(0).getValue(), is("http://liberation.fr"));
    //    assertThat(keywords.get(1).getValue(), is(canonicalUri));
    //}

    //@Test
    //public void addNewKeywordsToRepository() {
    //    final UriCreatedEvent event = createEventFor("http://liberation.fr");
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    assertThat(Repositories.keywords().getAll().size(), is(2));
    //}
    //
    //@Test
    //public void useExistingKeyword() {
    //    TestFactories.keywords().newKeyword(canonicalUri, SteambeatLanguage.none());
    //    final UriCreatedEvent event = createEventFor("http://liberation.fr");
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    assertThat(Repositories.keywords().getAll().size(), is(2));
    //}
    //
    //@Test
    //public void postAConceptCreatedEventOnError() {
    //    bus.capture(ConceptCreatedEvent.class);
    //    final UriCreatedEvent event = createEventFor(exceptionUri);
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final ConceptCreatedEvent conceptCreatedEvent = bus.lastEvent(ConceptCreatedEvent.class);
    //    assertThat(conceptCreatedEvent, notNullValue());
    //    assertThat(conceptCreatedEvent.getConcept().getKeywords().size(), is(1));
    //}
    //
    public UriCreatedEvent createEventFor(final String value) {
        final String address = value;
        final Uri uri = new Uri(address);
        final Keyword keyword = TestFactories.keywords().newKeyword(address, SteambeatLanguage.none());
        uri.setKeyword(keyword);
        return new UriCreatedEvent(uri);
    }

    private String canonicalUri;
    private String exceptionUri;
}
