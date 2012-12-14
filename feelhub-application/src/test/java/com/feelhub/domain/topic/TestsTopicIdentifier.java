package com.feelhub.domain.topic;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class TestsTopicIdentifier {

    @Test
    public void canIdentifyHttpTopics() {
        assertThat(TopicIdentifier.isHttpTopic("http://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("HTTP://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://www.sub.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://sub-test.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("https://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("https://www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("www.google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("www.google.com/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("google.com/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://google.com")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://google.com/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://google.com/bin/#")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("http://google.com/bin/#arf?id=true")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("yala.fr")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("yala.fr/")).isTrue();
        assertThat(TopicIdentifier.isHttpTopic("www.%C3%A9l%C3%A9phant.com")).isTrue();

        assertThat(TopicIdentifier.isHttpTopic("notanuri")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("httpnotanuri")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("http:notanuri")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("http:/notanuri")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("http://notanuri")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("http://notanuri/")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("http://notanuri/zala#lol")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("notanuri.comm")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic(".com")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic(".fr")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic(".c")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic(".come")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("www.%C3%A9l%C3%A9phant.")).isFalse();
    }

    @Test
    public void worldIsNotAnHttpTopic() {
        assertThat(TopicIdentifier.isHttpTopic("")).isFalse();
    }

    @Test
    public void canIdentifyFtp() {
        assertThat(TopicIdentifier.isHttpTopic("ftp://www.google.com")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("FTP://www.google.com")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("ftp://www.sub.google.com")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("ftp://sub-test.google.com")).isFalse();
        assertThat(TopicIdentifier.isHttpTopic("ftp://www.google.com/")).isFalse();

        assertThat(TopicIdentifier.isFtpTopic("ftp://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isFtpTopic("FTP://www.google.com")).isTrue();
        assertThat(TopicIdentifier.isFtpTopic("ftp://www.sub.google.com")).isTrue();
        assertThat(TopicIdentifier.isFtpTopic("ftp://sub-test.google.com")).isTrue();
        assertThat(TopicIdentifier.isFtpTopic("ftp://www.google.com/")).isTrue();
    }

    @Test
    public void canIdentifyImages() {
        assertThat(TopicIdentifier.isHttpTopic("http://www.google.com/image.jpg")).isTrue();
    }
}
