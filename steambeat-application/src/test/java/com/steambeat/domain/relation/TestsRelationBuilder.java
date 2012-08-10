package com.steambeat.domain.relation;

import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
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
    public void canConnectTwoSubjectWithNoPreviousConnection() {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(1.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    @Test
    public void canConnectTwoSubjectWithAnExistingOneWayRelation() {
        final Topic from = TestFactories.topics().newTopic();
        final Topic to = TestFactories.topics().newTopic();
        TestFactories.relations().newRelation(from, to);

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    private RelationBuilder relationBuilder;
}
