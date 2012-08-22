package com.steambeat.domain.illustration;

import com.steambeat.domain.bingsearch.FakeBingLink;
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

public class TestsIllustrationManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new IllustrationManager(new FakeBingLink(), new FakeSessionProvider());
    }

    @Test
    public void canCreateIllustration() {
        final Keyword first = TestFactories.keywords().newKeyword();
        final Keyword second = TestFactories.keywords().newKeyword();
        final ReferencesChangedEvent event = new ReferencesChangedEvent(first.getReferenceId());
        event.addReferenceToChange(second.getReferenceId());

        DomainEventBus.INSTANCE.post(event);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getReferenceId(), is(event.getNewReferenceId()));
        assertThat(illustrations.get(0).getLink(), is(first.getValue() + "link"));
    }

    @Test
    public void canMigrateTwoExistingIllustrations() {
        final Reference first = TestFactories.references().newReference();
        TestFactories.illustrations().newIllustration(first, "link1");
        final Reference second = TestFactories.references().newReference();
        final Illustration illustrationToChange = TestFactories.illustrations().newIllustration(second, "link2");
        final ReferencesChangedEvent event = new ReferencesChangedEvent(first.getId());
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
        final ReferencesChangedEvent event = new ReferencesChangedEvent(first.getId());
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
        final ReferencesChangedEvent event = new ReferencesChangedEvent(first.getId());
        event.addReferenceToChange(second.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        final Illustration foundIllustration = illustrations.get(0);
        assertThat(foundIllustration, is(illustration));
    }
}
