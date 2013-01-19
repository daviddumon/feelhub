package com.feelhub.domain.feeling;

public enum SentimentValue {
    good {
        @Override
        public int numericValue() {
            return 1;
        }
    }, bad{
        @Override
        public int numericValue() {
            return -1;
        }
    }, neutral, none;

    public int numericValue() {
        return 0;
    }
}
