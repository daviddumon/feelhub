package com.feelhub.repositories;

import com.feelhub.domain.keyword.world.World;
import com.feelhub.repositories.fakeRepositories.FakeKeywordRepository;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;


public class TestsFakeKeywordRepository {

    @Before
    public void before() {
        fakeKeywordRepository = new FakeKeywordRepository();
    }

    @Test
    public void canLookUpWorld() {
        final World world = new World(UUID.randomUUID());
        fakeKeywordRepository.add(world);

        final World worldFound = fakeKeywordRepository.world();

        assertThat(worldFound).isNotNull();
        assertThat(worldFound).isEqualTo(world);
    }

    private FakeKeywordRepository fakeKeywordRepository;
}
