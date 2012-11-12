package com.feelhub.application;

import com.feelhub.domain.keyword.KeywordFactory;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsWorldService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        worldService = new WorldService(new TopicFactory(), new KeywordFactory());
    }

    @Test
    public void canLookupWorld() {
        final World world = TestFactories.keywords().newWorld();

        final World worldFound = worldService.lookUpOrCreateWorld();

        assertThat(worldFound).isNotNull();
        assertThat(worldFound).isEqualTo(world);
        assertThat(worldFound.getTopicId()).isEqualTo(world.getTopicId());
    }

    @Test
    public void canCreateWorldIfItDoesNotExists() {
        worldService.lookUpOrCreateWorld();

        assertThat(Repositories.keywords().world()).isNotNull();
    }

    private WorldService worldService;
}
