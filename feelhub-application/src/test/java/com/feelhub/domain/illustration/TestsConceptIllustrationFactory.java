package com.feelhub.domain.illustration;

import com.feelhub.domain.bingsearch.FakeBingLink;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsConceptIllustrationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new ConceptIllustrationFactory(new FakeBingLink(), new FakeSessionProvider());
    }

    @Test
    public void canCreateIllustration() {
        final Reference reference = TestFactories.references().newReference();
        final String value = "value";
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(reference.getId(), value);

        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getReferenceId(), is(conceptIllustrationRequestEvent.getReferenceId()));
        assertThat(illustrations.get(0).getLink(), is(value + "link"));
    }

    @Test
    public void checkForExistingIllustration() {
        final Reference reference = TestFactories.references().newReference();
        final String value = "value";
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(reference.getId(), value);
        TestFactories.illustrations().newIllustration(reference);

        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
    }
}
