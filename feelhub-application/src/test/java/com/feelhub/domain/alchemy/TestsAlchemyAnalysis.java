package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.AlchemyJsonResults;
import com.feelhub.domain.tag.Tag;
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
        final Tag tag = TestFactories.tags().newWord();

        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(tag);

        assertThat(alchemyAnalysis.getId(), notNullValue());
        assertThat(alchemyAnalysis.getTopicId(), is(tag.getTopicId()));
        assertThat(alchemyAnalysis.getValue(), is(tag.getValue()));
    }

    @Test
    public void canAddLanguageFromAlchemyResult() {
        final Tag tag = TestFactories.tags().newWord();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(tag);
        final AlchemyJsonResults alchemyJsonResults = new AlchemyJsonResults();
        alchemyJsonResults.language = "english";

        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName(alchemyJsonResults.language));

        assertThat(alchemyAnalysis.getLanguageCode(), is("en"));
    }
}
