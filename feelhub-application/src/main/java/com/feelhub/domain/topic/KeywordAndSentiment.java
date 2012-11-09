package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.SentimentValue;

public class KeywordAndSentiment {

    public KeywordAndSentiment(final SentimentValue sentimentValue, final String text) {
        this.sentimentValue = sentimentValue;
        this.text = text;
    }

    public SentimentValue sentimentValue;
    public String text;
}
