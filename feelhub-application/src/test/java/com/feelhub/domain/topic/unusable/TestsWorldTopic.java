package com.feelhub.domain.topic.unusable;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsWorldTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateWorldTopic() {
        final UUID id = UUID.randomUUID();

        final WorldTopic topic = new WorldTopic(id);

        assertThat(topic.getId()).isEqualTo(id);
    }
}
