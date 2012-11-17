package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        relationBuilder = injector.getInstance(RelationBuilder.class);
    }

    @Test
    public void canConnectTwoTopicsWithNoPreviousConnection() {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(1.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    @Test
    public void canConnectTwoTopicsWithAnExistingOneWayRelation() {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();
        TestFactories.relations().newRelation(from.getId(), to.getId());

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    @Test
    public void doNotConnectTwoIdenticalTopics() {
        final Topic topic = TestFactories.topics().newTopic();

        relationBuilder.connectTwoWays(topic, topic);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(0));
    }

    private RelationBuilder relationBuilder;
}
