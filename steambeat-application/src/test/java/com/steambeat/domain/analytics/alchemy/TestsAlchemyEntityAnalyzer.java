package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@Ignore
public class TestsAlchemyEntityAnalyzer extends TestWithMongoRepository {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(AlchemyEntityProvider.class);
        analyzer = new AlchemyEntityAnalyzer(entityProvider, getProvider());
    }

    @Test
    public void canCreateConceptFromNonAmbiguousNamedEntity() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(1));

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(2));
        final Concept concept = (Concept) subjects.get(1);
        assertThat(concept.getText(), is("name0"));
    }

    @Test
    public void createRelationsBetweenConceptsAndPages() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(1));

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        final Subject concept = Repositories.subjects().getAll().get(1);
        testRelation(webpage, concept, relations.get(0));
        testRelation(concept, webpage, relations.get(1));
    }

    @Test
    public void canCreateMultipleConcepts() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(2));

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(3));
    }

    @Test
    public void canCreateRelationsBetweenAllConceptsAndWebpages() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(5));

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(30));
    }

    @Test
    public void initialRelationsHaveAWeightOfOne() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(1));

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getWeight(), is(1));
        assertThat(relations.get(1).getWeight(), is(1));
    }

    @Test
    public void dontCreateConceptsIfAlreadyExisting() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(2));

        analyzer.analyze(webpage);
        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(3));
    }

    private void testRelation(final Subject left, final Subject right, final Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private AlchemyEntityProvider entityProvider;
    private AlchemyEntityAnalyzer analyzer;
}
