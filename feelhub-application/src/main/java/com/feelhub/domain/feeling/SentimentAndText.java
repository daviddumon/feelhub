package com.feelhub.domain.feeling;

public class SentimentAndText {

    public SentimentAndText(final SentimentValue sentimentValue, final String text) {
        this.sentimentValue = sentimentValue;
        this.text = text;
    }

    public SentimentValue sentimentValue;
    public String text;
}
