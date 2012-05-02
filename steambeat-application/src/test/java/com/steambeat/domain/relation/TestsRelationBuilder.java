package com.steambeat.domain.relation;

import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
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
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(1.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    @Test
    public void canConnectTwoSubjectWithAnExistingOneWayRelation() {
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();
        TestFactories.relations().newRelation(from, to);

        relationBuilder.connectTwoWays(from, to);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
    }

    private RelationBuilder relationBuilder;
}
