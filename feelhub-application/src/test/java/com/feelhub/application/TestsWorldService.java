package com.feelhub.application;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.*;

public class TestsWorldService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        worldService = injector.getInstance(WorldService.class);
    }

    @Test
    public void canLookupWorld() {
        final Topic world = TestFactories.topics().newWorld();

        final Topic worldFound = worldService.lookUpOrCreateWorld();

        assertThat(worldFound).isNotNull();
        assertThat(worldFound).isEqualTo(world);
        assertThat(worldFound.getId()).isEqualTo(world.getId());
    }

    @Test
    public void canCreateWorldIfItDoesNotExists() {
        worldService.lookUpOrCreateWorld();

        assertThat(Repositories.topics().world()).isNotNull();
    }

    private WorldService worldService;
}
