package com.feelhub.domain.tag;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsTagIdentifier {

    @Test
    public void canIdentifyUris() {
        assertThat(TagIdentifier.isUri("http://www.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("HTTP://www.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("http://www.sub.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("http://sub-test.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("http://www.google.com/")).isTrue();
        assertThat(TagIdentifier.isUri("https://www.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("https://www.google.com/")).isTrue();
        assertThat(TagIdentifier.isUri("www.google.com")).isTrue();
        assertThat(TagIdentifier.isUri("www.google.com/")).isTrue();
        assertThat(TagIdentifier.isUri("google.com")).isTrue();
        assertThat(TagIdentifier.isUri("google.com/")).isTrue();
        assertThat(TagIdentifier.isUri("http://google.com")).isTrue();
        assertThat(TagIdentifier.isUri("http://google.com/")).isTrue();
        assertThat(TagIdentifier.isUri("http://google.com/bin/#")).isTrue();
        assertThat(TagIdentifier.isUri("http://google.com/bin/#arf?id=true")).isTrue();
        assertThat(TagIdentifier.isUri("yala.fr")).isTrue();
        assertThat(TagIdentifier.isUri("yala.fr/")).isTrue();
        assertThat(TagIdentifier.isUri("www.%C3%A9l%C3%A9phant.com")).isTrue();

        assertThat(TagIdentifier.isUri("notanuri")).isFalse();
        assertThat(TagIdentifier.isUri("httpnotanuri")).isFalse();
        assertThat(TagIdentifier.isUri("http:notanuri")).isFalse();
        assertThat(TagIdentifier.isUri("http:/notanuri")).isFalse();
        assertThat(TagIdentifier.isUri("http://notanuri")).isFalse();
        assertThat(TagIdentifier.isUri("http://notanuri/")).isFalse();
        assertThat(TagIdentifier.isUri("http://notanuri/zala#lol")).isFalse();
        assertThat(TagIdentifier.isUri("notanuri.comm")).isFalse();
        assertThat(TagIdentifier.isUri(".com")).isFalse();
        assertThat(TagIdentifier.isUri(".fr")).isFalse();
        assertThat(TagIdentifier.isUri(".c")).isFalse();
        assertThat(TagIdentifier.isUri(".come")).isFalse();
        assertThat(TagIdentifier.isUri("ftp://www.google.com")).isFalse();
        assertThat(TagIdentifier.isUri("www.%C3%A9l%C3%A9phant.")).isFalse();
    }

    @Test
    public void worldIsNotAnUri() {
        assertThat(TagIdentifier.isUri("")).isFalse();
    }
}
