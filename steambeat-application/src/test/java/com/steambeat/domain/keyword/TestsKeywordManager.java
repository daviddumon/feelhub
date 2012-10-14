package com.steambeat.domain.keyword;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.*;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.UUID;

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
    @Ignore
    public void canChangeKeywordsReferenceForAConcept() {
        final Keyword first = TestFactories.keywords().newKeyword();
        final Keyword second = TestFactories.keywords().newKeyword();

        keywordManager.migrate((UUID)first.getId(), Lists.newArrayList((UUID)second.getId()));

        assertThat(second.getReferenceId(), is(first.getReferenceId()));
    }

    private KeywordManager keywordManager;
}
