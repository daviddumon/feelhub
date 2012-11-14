package com.feelhub.web.dto;

import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsKeywordData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void keywordDataHasATopicId() {
        final Topic topic = TestFactories.topics().newTopic();

        final KeywordData keywordData = new KeywordData.Builder().topicId(topic.getId()).build();

        assertThat(keywordData.getTopicId(), is(topic.getId().toString()));
    }

    @Test
    public void topicIdDefaultValueIsEmpty() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getTopicId(), is(""));
    }

    @Test
    public void keywordDataHasAnIllustration() {
        final Illustration illustration = TestFactories.illustrations().newIllustration(TestFactories.topics().newTopic(), "mylink");

        final KeywordData keywordData = new KeywordData.Builder().illustration(illustration).build();

        assertThat(keywordData.getIllustrationLink(), is(illustration.getLink()));
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getIllustrationLink(), is(""));
    }

    @Test
    public void keywordDataHasAKeyword() {
        final Keyword keyword = TestFactories.keywords().newWord("keyword");

        final KeywordData keywordData = new KeywordData.Builder().keyword(keyword).build();

        assertThat(keywordData.getKeywordValue(), is(keyword.getValue()));
    }

    @Test
    public void keywordValueDefaultValueIsEmpty() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getKeywordValue(), is(""));
    }

    @Test
    public void keywordDataHasALanguageCode() {
        final FeelhubLanguage language = FeelhubLanguage.reference();

        final KeywordData keywordData = new KeywordData.Builder().language(language).build();

        assertThat(keywordData.getLanguageCode(), is(language.getCode()));
    }

    @Test
    public void languageCodeHasADefaultValue() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getLanguageCode(), is(FeelhubLanguage.none().getCode()));
    }

    @Test
    public void keywordDataHasASentimentValue() {
        final KeywordData keywordData = new KeywordData.Builder().sentimentValue(SentimentValue.good).build();

        assertThat(keywordData.getSentimentValue(), is(SentimentValue.good));
    }

    @Test
    public void sentimentHadADefaultValue() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getSentimentValue(), is(SentimentValue.none));
    }

    @Test
    public void keywordDataHasATypeValue() {
        final Word word = TestFactories.keywords().newWord();

        final KeywordData keywordData = new KeywordData.Builder().keyword(word).build();

        assertThat(keywordData.getTypeValue(), is(word.getClass().getSimpleName().toLowerCase()));
    }

    @Test
    public void typeHadADefaultValue() {
        final KeywordData keywordData = new KeywordData.Builder().build();

        assertThat(keywordData.getTypeValue(), is(""));
    }
}
