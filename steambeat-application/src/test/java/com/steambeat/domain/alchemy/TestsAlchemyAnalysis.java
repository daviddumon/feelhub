package com.steambeat.domain.alchemy;

import com.steambeat.domain.keyword.Keyword;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TestsAlchemyAnalysis {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnAlchemyAnalysisForAKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword();

        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(keyword);

        assertThat(alchemyAnalysis.getId(), notNullValue());
        assertThat(alchemyAnalysis.getReferenceId(), is(keyword.getReferenceId()));
        assertThat(alchemyAnalysis.getValue(), is(keyword.getValue()));
    }
}
