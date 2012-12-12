package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.TestFactories;

public class SentimentTestFactory {

    public Sentiment newSentiment() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        return new Sentiment(realTopic.getId(), SentimentValue.good);
    }

    public Sentiment newBadSentiment() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        return new Sentiment(realTopic.getId(), SentimentValue.bad);
    }

    public Sentiment newGoodSentiment() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        return new Sentiment(realTopic.getId(), SentimentValue.good);
    }

    public Sentiment newSentiment(final RealTopic realTopic, final SentimentValue sentimentValue) {
        return new Sentiment(realTopic.getId(), sentimentValue);
    }
}
