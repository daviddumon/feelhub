package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.application.AssociationService;
import com.steambeat.domain.alchemy.readmodel.*;
import com.steambeat.domain.association.Association;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.FakeUriPathResolver;
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
        entityProvider = mock(AlchemyEntityProvider.class);
        analyzer = new AlchemyEntityAnalyzer(entityProvider, new AssociationService(new FakeUriPathResolver()));
    }

    @Test
    public void canCreateConceptFromNonAmbiguousNamedEntity() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(1));

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(2));
        final Concept concept = (Concept) subjects.get(1);
        assertThat(concept.getShortDescription(), is("name0"));
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
    public void initialRelationWeightIsRelevanceOfEntityPlusOne() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entities(1);
        when(entityProvider.entitiesFor(webpage)).thenReturn(entities);

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getWeight(), is(entities.get(0).relevance + 1));
        assertThat(relations.get(1).getWeight(), is(entities.get(0).relevance + 1));
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

    @Test
    public void canCreateAssociationFromEntities() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(2));

        analyzer.analyze(webpage);

        assertThat(Repositories.associations().getAll().size(), is(5));
    }

    @Test
    public void addWeightToExistingConcepts() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entities(1));

        analyzer.analyze(webpage);
        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        assertThat(relations.get(0).getWeight(), is(3.0));
    }

    @Test
    public void dontUseBadEntities() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(TestFactories.alchemy().entitiesWithHalfBadOnes(10));

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(6));
    }

    @Test
    public void dontUseSmallEntities() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<AlchemyJsonEntity> entities = Lists.newArrayList();
        final AlchemyJsonEntity entity = new AlchemyJsonEntity();
        entity.text = "la";
        entity.disambiguated = new AlchemyJsonDisambiguated();
        entity.disambiguated.name = "longtext";
        entities.add(entity);
        when(entityProvider.entitiesFor(webpage)).thenReturn(entities);

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(1));
    }

    @Test
    public void trimEntities() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        final List<AlchemyJsonEntity> entities = Lists.newArrayList();
        final AlchemyJsonEntity entity = new AlchemyJsonEntity();
        entity.text = " needatrim ";
        entity.disambiguated = new AlchemyJsonDisambiguated();
        entity.disambiguated.name = " needatrim ";
        entities.add(entity);
        when(entityProvider.entitiesFor(webpage)).thenReturn(entities);

        analyzer.analyze(webpage);

        final List<Association> associations = Repositories.associations().getAll();
        assertThat(associations.get(1).getIdentifier(), is("needatrim"));
    }

    private void testRelation(final Subject left, final Subject right, final Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private AlchemyEntityProvider entityProvider;
    private AlchemyEntityAnalyzer analyzer;
}
