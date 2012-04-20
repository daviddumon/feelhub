package com.steambeat.domain.subject;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyXmlEntity;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeFactories.FakeWebPageFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSubjectFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        subjectFactory = new SubjectFactory(new FakeWebPageFactory());
    }

    @Test
    public void canCreateAWebPage() {
        final Uri uri = new Uri("lemonde.fr");
        final Association association = TestFactories.associations().newAssociation(uri);

        final WebPage webPage = subjectFactory.newWebPage(association);

        assertThat(webPage, notNullValue());
        assertThat(webPage.getId(), notNullValue());
        assertThat(webPage.getRealUri(), is(uri));
    }

    @Test
    public void canCreateConceptFromNamedEntity() {
        final AlchemyXmlEntity alchemyXmlEntity = new AlchemyXmlEntity();
        alchemyXmlEntity.text = "Agile";

        final Concept concept = subjectFactory.newConcept(alchemyXmlEntity);

        assertThat(concept, notNullValue());
        assertThat(concept.getText(), is("Agile"));
    }

    private SubjectFactory subjectFactory;
}
