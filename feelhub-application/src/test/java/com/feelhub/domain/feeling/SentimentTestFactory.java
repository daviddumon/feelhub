package com.feelhub.domain.feeling;

import com.feelhub.domain.reference.Reference;
import com.feelhub.test.TestFactories;

public class SentimentTestFactory {

    public Sentiment newSentiment() {
        final Reference reference = TestFactories.references().newReference();
        return new Sentiment(reference.getId(), SentimentValue.good);
    }

    public Sentiment newBadSentiment() {
        final Reference reference = TestFactories.references().newReference();
        return new Sentiment(reference.getId(), SentimentValue.bad);
    }

    public Sentiment newGoodSentiment() {
        final Reference reference = TestFactories.references().newReference();
        return new Sentiment(reference.getId(), SentimentValue.good);
    }

    public Sentiment newSentiment(final Reference reference, final SentimentValue sentimentValue) {
        return new Sentiment(reference.getId(), sentimentValue);
    }
}
