package com.feelhub.domain.feeling;

public class Subject {

    public Subject(final SentimentValue sentimentValue, final String text) {
        this.sentimentValue = sentimentValue;
        this.text = text;
    }

    public SentimentValue sentimentValue;
    public String text;
}
