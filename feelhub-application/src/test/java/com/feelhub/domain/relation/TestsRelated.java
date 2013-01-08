package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsRelated {

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
        related = relationFactory.newRelated(from, to, 1.0);
    }

    @Test
    public void canGetFromId() {
        assertThat(related.getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canGetFrom() {
        assertThat(related.getFrom()).isEqualTo(from);
    }

    @Test
    public void canGetToId() {
        assertThat(related.getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetTo() {
        assertThat(related.getTo()).isEqualTo(to);
    }

    @Test
    public void hasACreationDate() {
        assertThat(related.getCreationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void hasAWeight() {
        assertThat(related.getWeight()).isEqualTo(2.0);
    }

    private Related related;
    private Topic to;
    private Topic from;
    private RelationFactory relationFactory;
}
