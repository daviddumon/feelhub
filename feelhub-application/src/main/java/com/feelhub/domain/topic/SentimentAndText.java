package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.SentimentValue;

public class SentimentAndText {

    public SentimentAndText(final SentimentValue sentimentValue, final String text) {
        this.sentimentValue = sentimentValue;
        this.text = text;
    }

    public SentimentValue sentimentValue;
    public String text;
}
