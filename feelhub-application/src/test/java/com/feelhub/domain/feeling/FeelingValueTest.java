package com.feelhub.domain.feeling;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class FeelingValueTest {

    @Test
    public void hasANumericValue() {
        assertThat(FeelingValue.bored.numericValue()).isEqualTo(0);
        assertThat(FeelingValue.happy.numericValue()).isEqualTo(1);
        assertThat(FeelingValue.sad.numericValue()).isEqualTo(-1);
    }
}
