package com.feelhub.domain.feeling;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class FeelingValueTest {

    @Test
    public void hasANumericValue() {
        assertThat(FeelingValue.neutral.numericValue()).isEqualTo(0);
        assertThat(FeelingValue.good.numericValue()).isEqualTo(1);
        assertThat(FeelingValue.bad.numericValue()).isEqualTo(-1);
    }
}
