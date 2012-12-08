package com.feelhub.domain.topic.usable.web;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsWebTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAWebTopic() {
        final UUID id = UUID.randomUUID();
        final WebTopicType type = WebTopicType.Article;

        final WebTopic topic = new WebTopic(id, type);

        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCurrentId()).isEqualTo(id);
        assertThat(topic.getType()).isEqualTo(type);
    }

    @Test
    public void hasUrls() {
        final UUID id = UUID.randomUUID();
        final WebTopicType type = WebTopicType.Article;

        final WebTopic topic = new WebTopic(id, type);

        assertThat(topic.getUrls()).isNotNull();
        assertThat(topic.getUrls().size()).isZero();
    }

    @Test
    public void canAddUrl() {
        final UUID id = UUID.randomUUID();
        final WebTopicType type = WebTopicType.Article;
        final WebTopic topic = new WebTopic(id, type);
        final String url = "http://www.url.com";

        topic.addUrl(url);

        assertThat(topic.getUrls().size()).isEqualTo(1);
        assertThat(topic.getUrls().get(0)).isEqualTo(url);
    }
}
