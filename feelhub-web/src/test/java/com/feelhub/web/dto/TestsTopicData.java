package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsTopicData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void topicDataHasATopicId() {
        final Topic topic = TestFactories.topics().newTopic();

        final TopicData topicData = new TopicData.Builder().topicId(topic.getId()).build();

        assertThat(topicData.getTopicId(), is(topic.getId().toString()));
    }

    @Test
    public void topicIdDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getTopicId(), is(""));
    }

    @Test
    public void topicDataHasAnIllustration() {
        final Illustration illustration = TestFactories.illustrations().newIllustration(TestFactories.topics().newTopic(), "mylink");

        final TopicData topicData = new TopicData.Builder().illustration(illustration).build();

        assertThat(topicData.getIllustrationLink(), is(illustration.getLink()));
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getIllustrationLink(), is(""));
    }

    @Test
    public void topicDataHasAKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword("keyword");

        final TopicData topicData = new TopicData.Builder().keyword(keyword).build();

        assertThat(topicData.getKeywordValue(), is(keyword.getValue()));
    }

    @Test
    public void keywordValueDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getKeywordValue(), is(""));
    }

    @Test
    public void topicDataHasALanguageCode() {
        final FeelhubLanguage language = FeelhubLanguage.reference();

        final TopicData topicData = new TopicData.Builder().language(language).build();

        assertThat(topicData.getLanguageCode(), is(language.getCode()));
    }

    @Test
    public void languageCodeHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getLanguageCode(), is(FeelhubLanguage.none().getCode()));
    }

    @Test
    public void topicDataHasASentimentValue() {
        final TopicData topicData = new TopicData.Builder().sentimentValue(SentimentValue.good).build();

        assertThat(topicData.getSentimentValue(), is(SentimentValue.good));
    }

    @Test
    public void sentimentHadADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSentimentValue(), is(SentimentValue.none));
    }
}
