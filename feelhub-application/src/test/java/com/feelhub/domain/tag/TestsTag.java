package com.feelhub.domain.tag;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTag {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddATopicForALanguage() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final Tag tag = new Tag("value");

        tag.addTopic(realTopic, FeelhubLanguage.reference());

        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference())).isNotNull();
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference()).size()).isEqualTo(1);
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference()).get(0)).isEqualTo(realTopic.getId());
    }

    @Test
    public void canGetEmptyTopicList() {
        TestFactories.topics().newCompleteRealTopic();

        final Tag tag = new Tag("value");

        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference())).isNotNull();
        assertThat(tag.getTopicsIdFor(FeelhubLanguage.reference()).size()).isZero();
    }
}
