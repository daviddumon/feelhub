package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.ConceptReferencesChangedEvent;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsKeywordManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new KeywordManager(new FakeSessionProvider());
    }

    @Test
    public void canChangeKeywordsReferenceForAConcept() {
        final Keyword first = TestFactories.keywords().newKeyword();
        final Keyword second = TestFactories.keywords().newKeyword();
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(first.getReferenceId());
        event.addReferenceToChange(second.getReferenceId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(second.getReferenceId(), is(first.getReferenceId()));
    }
}
