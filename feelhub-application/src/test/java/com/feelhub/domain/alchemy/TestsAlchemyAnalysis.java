package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.AlchemyJsonResults;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

    @Test
    public void canAddLanguageFromAlchemyResult() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(keyword);
        final AlchemyJsonResults alchemyJsonResults = new AlchemyJsonResults();
        alchemyJsonResults.language = "english";

        alchemyAnalysis.setLanguageCode(FeelhubLanguage.forString(alchemyJsonResults.language));

        assertThat(alchemyAnalysis.getLanguageCode(), is("en"));
    }
}
