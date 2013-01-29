package com.feelhub.domain.media;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsMedia {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        mediaFactory = injector.getInstance(MediaFactory.class);
        from = TestFactories.topics().newCompleteRealTopic();
        to = TestFactories.topics().newCompleteRealTopic();
        media = mediaFactory.newMedia(from, to);
    }

    @Test
    public void canGetFromId() {
        assertThat(media.getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canGetToId() {
        assertThat(media.getToId()).isEqualTo(to.getId());
    }

    private Media media;
    private Topic to;
    private Topic from;
    private MediaFactory mediaFactory;
}
