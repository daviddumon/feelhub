package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSentiment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasATopicAndASentimentValue() {
        final Topic topic = TestFactories.topics().newTopic();
        final SentimentValue sentimentValue = SentimentValue.good;

        final Sentiment sentiment = new Sentiment(topic.getId(), sentimentValue);

        assertThat(sentiment, notNullValue());
        assertThat(sentiment.getTopicId(), is(topic.getId()));
        assertThat(sentiment.getSentimentValue(), is(sentimentValue));
    }
}
