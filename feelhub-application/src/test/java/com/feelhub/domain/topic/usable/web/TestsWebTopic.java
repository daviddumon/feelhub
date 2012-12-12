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
}
