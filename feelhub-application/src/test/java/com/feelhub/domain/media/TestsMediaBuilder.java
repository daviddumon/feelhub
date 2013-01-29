package com.feelhub.domain.media;

import com.feelhub.domain.related.Related;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsMediaBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        mediaBuilder = injector.getInstance(MediaBuilder.class);
    }

    @Test
    public void canConnectTwoTopicsWithNoPreviousConnection() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final HttpTopic media = TestFactories.topics().newMediaTopic();

        mediaBuilder.connectTwoWays(from, media);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isEqualTo(1);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(1.0);
        final List<Media> mediaList = Repositories.medias().getAll();
        assertThat(mediaList.size()).isEqualTo(1);
    }

    @Test
    public void canConnectTwoTopicsWithAnExistingOneWayRelated() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        final HttpTopic media = TestFactories.topics().newMediaTopic();
        TestFactories.related().newRelated(media.getId(), topic.getId());

        mediaBuilder.connectTwoWays(topic, media);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isEqualTo(1);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(2.0);
    }

    @Test
    public void doNotConnectTwoIdenticalTopics() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        mediaBuilder.connectTwoWays(realTopic, realTopic);

        final List<Related> relatedList = Repositories.related().getAll();
        assertThat(relatedList.size()).isZero();
        final List<Media> mediaList = Repositories.medias().getAll();
        assertThat(mediaList.size()).isZero();
    }

    @Test
    public void useExistingMediaRelation() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final HttpTopic media = TestFactories.topics().newMediaTopic();
        TestFactories.medias().newMedia(from.getId(), media.getId());

        mediaBuilder.connectTwoWays(from, media);

        final List<Media> mediaList = Repositories.medias().getAll();
        assertThat(mediaList.size()).isEqualTo(1);
    }

    private MediaBuilder mediaBuilder;
}
