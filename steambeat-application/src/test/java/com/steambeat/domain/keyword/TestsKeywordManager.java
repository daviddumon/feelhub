package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.reference.ReferencePatch;
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
        keywordManager = new KeywordManager();
    }

    @Test
    public void canChangeKeywordsReferenceForAConcept() {
        final Keyword first = TestFactories.keywords().newKeyword();
        final Keyword second = TestFactories.keywords().newKeyword();
        final ReferencePatch referencePatch = new ReferencePatch(first.getReferenceId());
        referencePatch.addOldReferenceId(second.getReferenceId());

        keywordManager.merge(referencePatch);

        assertThat(second.getReferenceId(), is(first.getReferenceId()));
    }

    private KeywordManager keywordManager;
}
