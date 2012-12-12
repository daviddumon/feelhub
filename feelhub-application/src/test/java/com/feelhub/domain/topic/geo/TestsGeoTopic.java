package com.feelhub.domain.topic.geo;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsGeoTopic {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAGeoTopic() {
        final UUID id = UUID.randomUUID();
        final GeoTopicType type = GeoTopicType.Coords;

        final GeoTopic topic = new GeoTopic(id, type);

        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCurrentId()).isEqualTo(id);
        assertThat(topic.getType()).isEqualTo(type);
    }
}
