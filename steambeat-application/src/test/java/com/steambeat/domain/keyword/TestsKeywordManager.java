package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.ReferencesChangedEvent;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
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
        new KeywordManager();
    }

    @Test
    public void canChangeReferenceForKeywords() {
        final Keyword first = TestFactories.keywords().newKeyword();
        final Keyword second = TestFactories.keywords().newKeyword();
        final ReferencesChangedEvent event = new ReferencesChangedEvent(first.getReferenceId());
        event.addIfAbsent(second.getReferenceId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(second.getReferenceId(), is(first.getReferenceId()));
    }
}
