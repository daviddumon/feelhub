package com.steambeat.domain.relation;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
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
        final Reference from = TestFactories.references().newReference();
        final Reference to = TestFactories.references().newReference();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(1.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    @Test
    public void canConnectTwoSubjectWithAnExistingOneWayRelation() {
        final Reference from = TestFactories.references().newReference();
        final Reference to = TestFactories.references().newReference();
        TestFactories.relations().newRelation(from, to);

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    private RelationBuilder relationBuilder;
}
