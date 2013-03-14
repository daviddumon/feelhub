package com.feelhub.domain.feeling;

public enum SentimentValue {
    good(1), bad(-1), neutral(0);

    SentimentValue(final int numericValue) {
        this.numericValue = numericValue;
    }

    public int numericValue() {
        return numericValue;
    }

    private final int numericValue;
}


