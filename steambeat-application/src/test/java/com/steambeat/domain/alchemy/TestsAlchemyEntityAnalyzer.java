package com.steambeat.domain.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.*;
import com.steambeat.test.fakeFactories.FakeConceptFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestsAlchemyEntityAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        analyzer = new AlchemyEntityAnalyzer(entityProvider, new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator()), new FakeConceptFactory());
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWithoutKeywords());

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(1));
        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.size(), is(1));
        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(0));
    }

    @Test
    public void doNotCreateConceptFromExistingConcept() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith1Keyword();
        when(entityProvider.entitiesFor(webpage)).thenReturn(namedEntities);

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(1));
    }

    @Test
    public void createConceptIfNoPreviousConcept() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(2));
    }

    @Test
    public void createAssociationForKeyword() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1Keyword());

        analyzer.analyze(webpage);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.size(), is(2));
    }

    @Test
    public void createAssociationFor2Keywords() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith2Keywords());

        analyzer.analyze(webpage);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.size(), is(3));
    }

    @Test
    public void createAssociationFromKeywordOnlyIfNotExisting() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith2Keywords();
        final NamedEntity namedEntity = namedEntities.get(0);
        TestFactories.associations().newAssociation(new Tag(namedEntity.keywords.get(0)), namedEntity.conceptId, namedEntity.language);
        when(entityProvider.entitiesFor(webpage)).thenReturn(namedEntities);

        analyzer.analyze(webpage);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.size(), is(3));
    }

    @Test
    public void dontCreateConceptsIfAlreadyExisting() {
        final WebPage webpage = TestFactories.subjects().newWebPage();

        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());
        analyzer.analyze(webpage);
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1Keyword());
        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(2));
    }

    @Test
    public void createRelationsBetweenConceptsAndPages() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        final Subject concept = Repositories.subjects().getAll().get(1);
        testRelation(webpage, concept, relations.get(0));
        testRelation(concept, webpage, relations.get(1));
    }

    @Test
    public void canCreateRelationsBetweenAllConceptsAndWebpages() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntitiesWithoutConcepts(5));

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(30));
    }

    @Test
    public void initialRelationWeightIsRelevanceOfEntityPlusOne() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept();
        when(entityProvider.entitiesFor(webpage)).thenReturn(namedEntities);

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getWeight(), is(namedEntities.get(0).relevance + 1));
        assertThat(relations.get(1).getWeight(), is(namedEntities.get(0).relevance + 1));
    }

    @Test
    public void addWeightToExistingConcepts() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());

        analyzer.analyze(webpage);
        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(3.0));
    }

    private void testRelation(final Subject left, final Subject right, final Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private NamedEntityProvider entityProvider;
    private AlchemyEntityAnalyzer analyzer;
}
