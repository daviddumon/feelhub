package com.feelhub.application;

import com.feelhub.domain.topic.unusable.WorldTopic;
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
        final WorldTopic worldTopic = TestFactories.topics().newWorldTopic();

        final WorldTopic worldTopicFound = worldService.lookUpOrCreateWorld();

        assertThat(worldTopicFound).isNotNull();
        assertThat(worldTopicFound).isEqualTo(worldTopic);
    }

    @Test
    public void canCreateWorldIfItDoesNotExists() {
        worldService.lookUpOrCreateWorld();

        assertThat(Repositories.topics().getWorldTopic()).isNotNull();
    }

    private WorldService worldService;
}
