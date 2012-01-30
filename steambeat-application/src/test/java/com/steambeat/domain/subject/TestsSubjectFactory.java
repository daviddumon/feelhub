package com.steambeat.domain.subject;

import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.domain.textAnalytics.NamedEntity;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectFactory {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        subjectFactory = new SubjectFactory();
    }

    @Test
    public void canBuildWebPage() {
        final Association association = TestFactories.associations().newAssociation("lemonde.fr");

        final WebPage webPage = subjectFactory.createWebPage(association);

        assertThat(webPage, notNullValue());
        assertThat(webPage.getId(), is("http://lemonde.fr"));
    }

    @Test
    public void canCreateConceptFromNamedEntity() {
        final NamedEntity namedEntity = new NamedEntity();
        namedEntity.text = "Agile";

        final Concept concept = subjectFactory.createConcept(namedEntity);

        assertThat(concept, notNullValue());
        assertThat(concept.getText(), is("Agile"));
    }

    private SubjectFactory subjectFactory;
}
