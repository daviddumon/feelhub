package com.feelhub.domain.feeling;

public enum FeelingValue {

    happy(1),
    sad(-1),
    bored(0);

    FeelingValue(final int numericValue) {
        this.numericValue = numericValue;
    }

    public int numericValue() {
        return numericValue;
    }

    private final int numericValue;
}