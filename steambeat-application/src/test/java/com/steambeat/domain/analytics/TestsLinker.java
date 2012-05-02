package com.steambeat.domain.analytics;

import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLinker {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        linker = new Linker(new RelationFactory());
    }

    @Test
    public void canConnectTwoSubjectWithNoPreviousConnection() {
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();

        linker.connectTwoWays(from, to);

        assertThat(Repositories.relations().getAll().size(), is(2));
    }

    @Test
    @Ignore
    public void canConnectTwoSubjectWithAnExistingOneWayRelation() {
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();
        TestFactories.relations().newRelation(from, to);

        linker.connectTwoWays(from, to);

        assertThat(Repositories.relations().getAll().size(), is(2));
    }

    private Linker linker;
}
