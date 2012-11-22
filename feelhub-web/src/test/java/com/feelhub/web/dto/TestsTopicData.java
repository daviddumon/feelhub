package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


public class TestsTopicData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void topicDataHasId() {
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().id(topic.getId()).build();

        assertThat(topicData.getId()).isEqualTo(topic.getId().toString());
    }

    @Test
    public void idDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getId()).isEmpty();
    }

    @Test
    public void topicDataHasAnIllustration() {
        final Illustration illustration = TestFactories.illustrations().newIllustration(TestFactories.topics().newTopic().getId(), "mylink");

        final TopicData topicData = new TopicData.Builder().illustration(illustration).build();

        assertThat(topicData.getIllustrationLink()).isEqualTo(illustration.getLink());
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getIllustrationLink()).isEmpty();
    }

    @Test
    public void topicDataHasADescription() {
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().description(topic.getDescription(FeelhubLanguage.reference())).build();

        assertThat(topicData.getDescription()).isEqualTo(topic.getDescription(FeelhubLanguage.reference()));
    }

    @Test
    public void descriptionValueDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getDescription()).isEmpty();
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
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().type(topic.getType()).build();

        assertThat(topicData.getType()).isEqualTo(topic.getType());
    }

    @Test
    public void typeHadADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getType()).isEqualTo(TopicTypes.none);
    }

    @Test
    public void topicHasSubtypes() {
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().subtypes(topic.getSubTypes()).build();

        assertThat(topicData.getSubTypes()).isEqualTo(topic.getSubTypes());
    }

    @Test
    public void subTypesHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSubTypes()).isEmpty();
    }

    @Test
    public void topicHasUrls() {
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().urls(topic.getUrls()).build();

        assertThat(topicData.getUrls()).isEqualTo(topic.getUrls());
    }

    @Test
    public void urlsHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getUrls()).isEmpty();
    }
}
