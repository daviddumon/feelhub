package com.feelhub.domain.topic;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsTopicIdentifier {

    @Test
    public void canIdentifyUris() {
        assertThat(TopicIdentifier.isWebTopic("http://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("HTTP://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://www.sub.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://sub-test.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("https://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("https://www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("google.com/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://google.com/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://google.com/bin/#")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("http://google.com/bin/#arf?id=true")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("yala.fr")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("yala.fr/")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("www.%C3%A9l%C3%A9phant.com")).isTrue();

        assertThat(TopicIdentifier.isWebTopic("notanuri")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("httpnotanuri")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("http:notanuri")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("http:/notanuri")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("http://notanuri")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("http://notanuri/")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("http://notanuri/zala#lol")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("notanuri.comm")).isFalse();
        assertThat(TopicIdentifier.isWebTopic(".com")).isFalse();
        assertThat(TopicIdentifier.isWebTopic(".fr")).isFalse();
        assertThat(TopicIdentifier.isWebTopic(".c")).isFalse();
        assertThat(TopicIdentifier.isWebTopic(".come")).isFalse();
        assertThat(TopicIdentifier.isWebTopic("www.%C3%A9l%C3%A9phant.")).isFalse();
    }

    @Test
    public void worldIsNotAnUri() {
        assertThat(TopicIdentifier.isWebTopic("")).isFalse();
    }

    @Test
    public void canIdentifyFtp() {
        assertThat(TopicIdentifier.isWebTopic("ftp://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("FTP://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("ftp://www.sub.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("ftp://sub-test.google.com")).isTrue();
        assertThat(TopicIdentifier.isWebTopic("ftp://www.google.com/")).isTrue();
    }

    @Test
    public void canIdentifyImages() {
        assertThat(TopicIdentifier.isWebTopic("http://www.google.com/image.jpg")).isTrue();
    }
}
