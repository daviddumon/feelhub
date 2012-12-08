package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsRelation {

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
        relation = relationFactory.newRelation(from, to, 1.0);
    }

    @Test
    public void canGetFromId() {
        assertThat(relation.getFromId()).isEqualTo(from.getId());
    }

    @Test
    public void canGetFrom() {
        assertThat(relation.getFrom()).isEqualTo(from);
    }

    @Test
    public void canGetToId() {
        assertThat(relation.getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetTo() {
        assertThat(relation.getTo()).isEqualTo(to);
    }

    @Test
    public void hasACreationDate() {
        assertThat(relation.getCreationDate()).isEqualTo(time.getNow());
    }

    private Relation relation;
    private Topic to;
    private Topic from;
    private RelationFactory relationFactory;
}
