package com.feelhub.domain.relation;

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
        relationFactory = injector.getInstance(RelationFactory.class);
        from = TestFactories.topics().newCompleteRealTopic();
        to = TestFactories.topics().newCompleteRealTopic();
        media = relationFactory.newMedia(from, to);
    }

    @Test
    public void canGetFromId() {
        assertThat(media.getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canGetFrom() {
        assertThat(media.getFrom()).isEqualTo(from);
    }

    @Test
    public void canGetToId() {
        assertThat(media.getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetTo() {
        assertThat(media.getTo()).isEqualTo(to);
    }

    @Test
    public void hasACreationDate() {
        assertThat(media.getCreationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void weightIsZero() {
        assertThat(media.getWeight()).isZero();
    }

    private Media media;
    private Topic to;
    private Topic from;
    private RelationFactory relationFactory;
}
