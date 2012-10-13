package com.steambeat.domain.illustration;

import com.steambeat.domain.bingsearch.FakeBingLink;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
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
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(keyword.getReferenceId());

        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0).getReferenceId(), is(conceptIllustrationRequestEvent.getReferenceId()));
        assertThat(illustrations.get(0).getLink(), is(keyword.getValue() + "link"));
    }
}
