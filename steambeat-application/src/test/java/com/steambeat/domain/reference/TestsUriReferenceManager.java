package com.steambeat.domain.reference;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriReferenceManager {
    //
    //@Rule
    //public WithDomainEvent bus = new WithDomainEvent();
    //
    //@Rule
    //public WithFakeRepositories repositories = new WithFakeRepositories();
    //
    //@Rule
    //public SystemTime time = SystemTime.fixed();
    //
    //@Before
    //public void before() {
    //    new UriReferenceManager(new FakeSessionProvider());
    //}
    //
    //@Test
    //public void setOnlyTheOldestReferenceAsActive() {
    //    final CompleteUriEvent event = TestFactories.events().newCompleteUriEvent();
    //    final Keyword keyword1 = TestFactories.keywords().newKeyword("http://google.fr", SteambeatLanguage.none());
    //    time.waitDays(1);
    //    final Keyword keyword2 = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none());
    //    event.addIfAbsent(keyword1);
    //    event.addIfAbsent(keyword2);
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final List<Reference> references = Repositories.references().getAll();
    //    assertThat(references.get(0).isActive(), is(true));
    //    assertThat(references.get(1).isActive(), is(false));
    //}
    //
    //@Test
    //public void postAUriReferencesChangedEvent() {
    //    bus.capture(UriReferencesChangedEvent.class);
    //    final CompleteUriEvent event = TestFactories.events().newCompleteUriEvent();
    //    event.addIfAbsent(TestFactories.keywords().newKeyword("fr", SteambeatLanguage.forString("fr")));
    //    event.addIfAbsent(TestFactories.keywords().newKeyword("en", SteambeatLanguage.forString("en")));
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final UriReferencesChangedEvent lastEvent = bus.lastEvent(UriReferencesChangedEvent.class);
    //    assertThat(lastEvent, notNullValue());
    //    assertThat(lastEvent.getReferenceIds().size(), is(1));
    //    assertThat(lastEvent.getNewReferenceId(), notNullValue());
    //}
}
