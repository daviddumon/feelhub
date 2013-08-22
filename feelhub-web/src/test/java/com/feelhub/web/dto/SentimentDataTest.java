package com.feelhub.web.dto;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.UnusableTopicTypes;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class SentimentDataTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasId() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final SentimentData sentimentData = new SentimentData.Builder().id(realTopic.getId()).build();

        assertThat(sentimentData.getId()).isEqualTo(realTopic.getId().toString());
    }

    @Test
    public void idDefaultValueIsEmpty() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getId()).isEmpty();
    }

    @Test
    public void hasAThumbnail() {
        final String thumbnail = "mylink";

        final SentimentData sentimentData = new SentimentData.Builder().thumbnail(thumbnail).build();

        assertThat(sentimentData.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    public void thumbnailIsDefaultValueEmpty() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getThumbnail()).isEmpty();
    }

    @Test
    public void hasAName() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final SentimentData sentimentData = new SentimentData.Builder().name(realTopic.getName(FeelhubLanguage.reference())).build();

        assertThat(sentimentData.getName()).isEqualTo(realTopic.getName(FeelhubLanguage.reference()));
    }

    @Test
    public void nameValueDefaultValueIsEmpty() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getName()).isEmpty();
    }

    @Test
    public void hasASentimentValue() {
        final SentimentData sentimentData = new SentimentData.Builder().sentimentValue(SentimentValue.good).build();

        assertThat(sentimentData.getSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void sentimentHadADefaultValue() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getSentimentValue()).isEqualTo(SentimentValue.neutral);
    }

    @Test
    public void hasAType() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final SentimentData sentimentData = new SentimentData.Builder().type(realTopic.getType()).build();

        assertThat(sentimentData.getType()).isEqualTo(realTopic.getType().toString());
    }

    @Test
    public void hasTopicTypeDefault() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getType()).isEqualTo(UnusableTopicTypes.None.toString());
    }

    @Test
    public void descriptionValueDefaultValueIsEmpty() {
        final SentimentData sentimentData = new SentimentData.Builder().build();

        assertThat(sentimentData.getName()).isEmpty();
    }
}
