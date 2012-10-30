package com.feelhub.domain.keyword;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.reference.ReferencePatch;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
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
