package com.feelhub.domain.keyword;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsKeywordIdentifier {

    @Test
    public void canIdentifyUris() {
        assertThat(KeywordIdentifier.isUri("http://www.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("HTTP://www.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://www.sub.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://sub-test.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://www.google.com/")).isTrue();
        assertThat(KeywordIdentifier.isUri("https://www.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("https://www.google.com/")).isTrue();
        assertThat(KeywordIdentifier.isUri("www.google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("www.google.com/")).isTrue();
        assertThat(KeywordIdentifier.isUri("google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("google.com/")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://google.com")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://google.com/")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://google.com/bin/#")).isTrue();
        assertThat(KeywordIdentifier.isUri("http://google.com/bin/#arf?id=true")).isTrue();
        assertThat(KeywordIdentifier.isUri("yala.fr")).isTrue();
        assertThat(KeywordIdentifier.isUri("yala.fr/")).isTrue();
        assertThat(KeywordIdentifier.isUri("www.%C3%A9l%C3%A9phant.com")).isTrue();

        assertThat(KeywordIdentifier.isUri("notanuri")).isFalse();
        assertThat(KeywordIdentifier.isUri("httpnotanuri")).isFalse();
        assertThat(KeywordIdentifier.isUri("http:notanuri")).isFalse();
        assertThat(KeywordIdentifier.isUri("http:/notanuri")).isFalse();
        assertThat(KeywordIdentifier.isUri("http://notanuri")).isFalse();
        assertThat(KeywordIdentifier.isUri("http://notanuri/")).isFalse();
        assertThat(KeywordIdentifier.isUri("http://notanuri/zala#lol")).isFalse();
        assertThat(KeywordIdentifier.isUri("notanuri.comm")).isFalse();
        assertThat(KeywordIdentifier.isUri(".com")).isFalse();
        assertThat(KeywordIdentifier.isUri(".fr")).isFalse();
        assertThat(KeywordIdentifier.isUri(".c")).isFalse();
        assertThat(KeywordIdentifier.isUri(".come")).isFalse();
        assertThat(KeywordIdentifier.isUri("ftp://www.google.com")).isFalse();
        assertThat(KeywordIdentifier.isUri("www.%C3%A9l%C3%A9phant.")).isFalse();
    }

    @Test
    public void worldIsNotAnUri() {
        assertThat(KeywordIdentifier.isUri("")).isFalse();
    }
}
