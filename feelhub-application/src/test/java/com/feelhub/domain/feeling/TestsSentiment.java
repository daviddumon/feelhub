package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;

public class TestsSentiment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasATopicAndASentimentValue() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final SentimentValue sentimentValue = SentimentValue.good;

        final Sentiment sentiment = new Sentiment(realTopic.getId(), sentimentValue);

        assertThat(sentiment).isNotNull();
        assertThat(sentiment.getTopicId()).isEqualTo(realTopic.getId());
        assertThat(sentiment.getSentimentValue()).isEqualTo(sentimentValue);
    }

    @Test
    public void keepTokenInSentiment() {
        final String token = "token";

        final Sentiment sentiment = new Sentiment(SentimentValue.good, token);

        assertThat(sentiment.getToken()).isEqualTo(token);
    }
}
