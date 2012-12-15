package com.feelhub.domain.alchemy;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsAlchemyAnalysis {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnAlchemyAnalysis() {
        final HttpTopic topic = TestFactories.topics().newCompleteHttpTopic();

        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(topic);

        assertThat(alchemyAnalysis.getId()).isNotNull();
        assertThat(alchemyAnalysis.getTopicId()).isEqualTo(topic.getId());
        assertThat(alchemyAnalysis.getValue()).isEqualTo(topic.getUris().get(0).toString());
    }

    @Test
    public void canAddLanguage() {
        final HttpTopic topic = TestFactories.topics().newCompleteHttpTopic();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(topic);

        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName("english"));

        assertThat(alchemyAnalysis.getLanguageCode()).isEqualTo("en");
    }
}
