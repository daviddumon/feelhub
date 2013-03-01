package com.feelhub.domain.related;

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
        final RelatedFactory relatedFactory = injector.getInstance(RelatedFactory.class);
        from = TestFactories.topics().newCompleteRealTopic();
        to = TestFactories.topics().newCompleteRealTopic();
        related = relatedFactory.newRelated(from, to, 1.0);
    }

    @Test
    public void canGetFromId() {
        assertThat(related.getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canGetToId() {
        assertThat(related.getToId()).isEqualTo(to.getId());
    }

    @Test
    public void hasAWeight() {
        assertThat(related.getWeight()).isEqualTo(2.0);
    }

    private Related related;
    private Topic to;
    private Topic from;
}
