package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.repositories.fakeRepositories.FakeTopicRepository;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsFakeTopicRepository {

    @Before
    public void before() {
        fakeKeywordRepository = new FakeTopicRepository();
    }

    @Test
    public void canLookUpWorld() {
        final Topic world = new Topic(UUID.randomUUID());
        world.setType(TopicType.World);
        fakeKeywordRepository.add(world);

        final Topic worldFound = fakeKeywordRepository.world();

        assertThat(worldFound).isNotNull();
        assertThat(worldFound).isEqualTo(world);
    }

    private FakeTopicRepository fakeKeywordRepository;
}
