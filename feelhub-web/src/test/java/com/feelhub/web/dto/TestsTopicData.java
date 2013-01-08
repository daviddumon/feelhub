package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.UnusableTopicTypes;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


public class TestsTopicData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void topicDataHasId() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().id(realTopic.getId()).build();

        assertThat(topicData.getId()).isEqualTo(realTopic.getId().toString());
    }

    @Test
    public void idDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getId()).isEmpty();
    }

    @Test
    @Ignore
    public void topicDataHasAnIllustration() {
        //final Illustration illustration = TestFactories.illustrations().newIllustration(TestFactories.topics().newCompleteRealTopic().getId(), "mylink");
        //
        //final TopicData topicData = new TopicData.Builder().illustration(illustration).build();
        //
        //assertThat(topicData.getIllustration()).isEqualTo(illustration.getLink());
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getIllustration()).isEmpty();
    }

    @Test
    public void topicDataHasADescription() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().name(realTopic.getDescription(FeelhubLanguage.reference())).build();

        assertThat(topicData.getName()).isEqualTo(realTopic.getDescription(FeelhubLanguage.reference()));
    }

    @Test
    public void descriptionValueDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getName()).isEmpty();
    }

    @Test
    public void topicDataHasASentimentValue() {
        final TopicData topicData = new TopicData.Builder().sentimentValue(SentimentValue.good).build();

        assertThat(topicData.getSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void sentimentHadADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSentimentValue()).isEqualTo(SentimentValue.none);
    }

    @Test
    public void topicDataHasAType() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().type(realTopic.getType()).build();

        assertThat(topicData.getType()).isEqualTo(realTopic.getType().toString());
    }

    @Test
    public void hasTopicTypeDefault() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getType()).isEqualTo(UnusableTopicTypes.None.toString());
    }

    @Test
    public void topicHasSubtypes() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().subtypes(realTopic.getSubTypes()).build();

        assertThat(topicData.getSubTypes()).isEqualTo(realTopic.getSubTypes());
    }

    @Test
    public void subTypesHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSubTypes()).isEmpty();
    }

    @Test
    @Ignore
    public void topicHasUrls() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        //final TopicData topicData = new TopicData.Builder().urls(realTopic.getUrls()).build();
        //
        //assertThat(topicData.getUrls()).isEqualTo(realTopic.getUrls());
    }

    @Test
    public void urlsHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getUrls()).isEmpty();
    }

    @Test
    public void canRepresentInString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicData topicData = new TopicData.Builder().id(realTopic.getId()).type(realTopic.getType()).build();

        assertThat(topicData.toString()).isEqualTo("{\"sentimentValue\":{},\"id\":\"" + realTopic.getId() + "\",\"urls\":[],\"illustration\":\"\",\"name\":\"\",\"subTypes\":[],\"type\":\"Automobile\"}");
    }

}
