package com.feelhub.tools;

import org.apache.commons.lang.WordUtils;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsWordUtils {

    @Test
    public void canCapitalize() {
        assertThat(test("john", "John")).isTrue();
        assertThat(test("The see", "The See")).isTrue();
        assertThat(test("a cat", "A Cat")).isTrue();
        assertThat(test("david d", "David D")).isTrue();
        assertThat(test("http://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm", "http://seriouswheels.com/cars/2010/top-2010-Aston-Martin-DBS-Volante.htm")).isFalse();
    }

    private boolean test(final String before, final String after) {
        return WordUtils.capitalizeFully(before).equals(after);
    }
}
