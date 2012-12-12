package com.feelhub.domain.alchemy;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsAlchemyAnalysis {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnAlchemyAnalysis() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final String value = "http://www.fakeurl.com";

        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(realTopic, value);

        assertThat(alchemyAnalysis.getId()).isNotNull();
        assertThat(alchemyAnalysis.getTopicId()).isEqualTo(realTopic.getId());
        assertThat(alchemyAnalysis.getValue()).isEqualTo(value);
    }

    @Test
    public void canAddLanguage() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final String value = "http://www.fakeurl.com";
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(realTopic, value);

        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName("english"));

        assertThat(alchemyAnalysis.getLanguageCode()).isEqualTo("en");
    }
}
