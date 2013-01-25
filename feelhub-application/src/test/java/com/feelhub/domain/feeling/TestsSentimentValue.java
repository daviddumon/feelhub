package com.feelhub.domain.feeling;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsSentimentValue {

    @Test
    public void hasANumericValue() {
        assertThat(SentimentValue.none.numericValue()).isEqualTo(0);
        assertThat(SentimentValue.neutral.numericValue()).isEqualTo(0);
        assertThat(SentimentValue.good.numericValue()).isEqualTo(1);
        assertThat(SentimentValue.bad.numericValue()).isEqualTo(-1);
    }
}
