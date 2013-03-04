package com.feelhub.domain.media;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class MediaManagerTest {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        mediaManager = injector.getInstance(MediaManager.class);
    }

    @Test
    public void canChangeMediaTopics() {
        final HttpTopic httpTopic1 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic2 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic3 = TestFactories.topics().newMediaTopic();
        final Media media1 = TestFactories.medias().newMedia(httpTopic2.getId(), httpTopic3.getId());
        final Media media2 = TestFactories.medias().newMedia(httpTopic3.getId(), httpTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(httpTopic1.getId());
        topicPatch.addOldTopicId(httpTopic2.getId());

        mediaManager.merge(topicPatch);

        assertThat(media1.getFromId(), is(httpTopic1.getId()));
        assertThat(media1.getToId(), is(httpTopic3.getId()));
        assertThat(media2.getFromId(), is(httpTopic3.getId()));
        assertThat(media2.getToId(), is(httpTopic1.getId()));
    }

    @Test
    public void canRemoveAutoMedia() {
        final HttpTopic httpTopic1 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic2 = TestFactories.topics().newMediaTopic();
        TestFactories.medias().newMedia(httpTopic1.getId(), httpTopic2.getId());
        TestFactories.medias().newMedia(httpTopic2.getId(), httpTopic1.getId());
        final TopicPatch topicPatch = new TopicPatch(httpTopic1.getId());
        topicPatch.addOldTopicId(httpTopic2.getId());

        mediaManager.merge(topicPatch);

        assertThat(Repositories.medias().getAll().size(), is(0));
    }

    @Test
    public void canMergeDuplicate() {
        final HttpTopic httpTopic1 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic2 = TestFactories.topics().newMediaTopic();
        final HttpTopic httpTopic3 = TestFactories.topics().newMediaTopic();
        TestFactories.medias().newMedia(httpTopic1.getId(), httpTopic2.getId());
        TestFactories.medias().newMedia(httpTopic3.getId(), httpTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(httpTopic1.getId());
        topicPatch.addOldTopicId(httpTopic3.getId());

        mediaManager.merge(topicPatch);

        final List<Media> mediaList = Repositories.medias().getAll();
        assertThat(mediaList.size(), is(1));
    }

    private MediaManager mediaManager;
}
