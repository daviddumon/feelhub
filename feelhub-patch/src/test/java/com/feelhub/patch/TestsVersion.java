package com.feelhub.patch;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsVersion {

    @Test
    public void canToString() {
        final Version version = new Version(1151);

        assertThat(version.toString()).isEqualTo("1151");
    }

    @Test
    public void peutComparer() {
        assertThat(new Version(151).compareTo(new Version(151))).isEqualTo(0);
        assertThat(new Version(1).compareTo(new Version(2))).isEqualTo(-1);
        assertThat(new Version(2).compareTo(new Version(1))).isEqualTo(1);
    }

}
