package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.Topic;
import com.feelhub.test.TestFactories;

public class SentimentTestFactory {

    public Sentiment newSentiment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Sentiment(topic.getId(), SentimentValue.good);
    }

    public Sentiment newBadSentiment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Sentiment(topic.getId(), SentimentValue.bad);
    }

    public Sentiment newGoodSentiment() {
        final Topic topic = TestFactories.topics().newTopic();
        return new Sentiment(topic.getId(), SentimentValue.good);
    }

    public Sentiment newSentiment(final Topic topic, final SentimentValue sentimentValue) {
        return new Sentiment(topic.getId(), sentimentValue);
    }
}
