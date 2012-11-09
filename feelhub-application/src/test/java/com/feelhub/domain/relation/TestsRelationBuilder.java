package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        relationBuilder = new RelationBuilder(new RelationFactory());
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
        TestFactories.relations().newRelation(from, to);

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
