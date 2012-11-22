package com.feelhub.domain.alchemy;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsAlchemyAnalysis {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnAlchemyAnalysis() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "http://www.fakeurl.com";

        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(topic, value);

        assertThat(alchemyAnalysis.getId()).isNotNull();
        assertThat(alchemyAnalysis.getTopicId()).isEqualTo(topic.getId());
        assertThat(alchemyAnalysis.getValue()).isEqualTo(value);
    }

    @Test
    public void canAddLanguage() {
        final Topic topic = TestFactories.topics().newTopic();
        final String value = "http://www.fakeurl.com";
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(topic, value);

        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName("english"));

        assertThat(alchemyAnalysis.getLanguageCode()).isEqualTo("en");
    }
}
