package com.steambeat.domain.illustration;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriIllustrationManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new UriIllustrationManager(new FakeSessionProvider());
    }

    @Test
    public void canMigrateTwoExistingIllustrations() {
        final Reference first = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        final Illustration illustrationToChange = TestFactories.illustrations().newIllustration(second, "link2");
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(first.getId());
        event.addReferenceToChange(second.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(illustrationToChange.getReferenceId(), is(first.getId()));
    }

    @Test
    public void canDeleteDuplicateIllustrations() {
        final Reference first = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(second, "link2");
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(first.getId());
        event.addReferenceToChange(second.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }

    @Test
    public void doNotCreateIlAlreadyOneIllustrationExists() {
        final Reference first = TestFactories.references().newReference();
        final Illustration illustration = TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(first.getId());
        event.addReferenceToChange(second.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        final Illustration foundIllustration = illustrations.get(0);
        assertThat(foundIllustration, is(illustration));
    }

    @Test
    public void canRequestIllustration() {
        bus.capture(UriIllustrationRequestEvent.class);
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final UriReferencesChangedEvent uriReferencesChangedEvent = TestFactories.events().newUriReferencesChangedEvent(keyword.getReferenceId());

        DomainEventBus.INSTANCE.post(uriReferencesChangedEvent);

        final UriIllustrationRequestEvent uriIllustrationRequestEvent = bus.lastEvent(UriIllustrationRequestEvent.class);
        assertThat(uriIllustrationRequestEvent, notNullValue());
    }
}
