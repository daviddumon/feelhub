package com.steambeat.domain.analytics.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
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
        analyzer = new AlchemyEntityAnalyzer(entityProvider);
    }

    @Test
    public void canCreateConceptFromNonAmbiguousNamedEntity() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(Lists.newArrayList(simpleEntity()));

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(2));
        final Concept concept = (Concept) subjects.get(1);
        assertThat(concept.getText(), is("Agile"));
    }

    @Test
    public void createRelationsBetweenConceptsAndPages() {
        final WebPage webpage = TestFactories.subjects().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(Lists.newArrayList(simpleEntity()));

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
        when(entityProvider.entitiesFor(webpage)).thenReturn(complexNamedEntities());

        analyzer.analyze(webpage);

        final List<Subject> subjects = Repositories.subjects().getAll();
        assertThat(subjects.size(), is(3));
    }

    private List<AlchemyJsonEntity> complexNamedEntities() {
        List<AlchemyJsonEntity> result = Lists.newArrayList();
        final AlchemyJsonEntity alchemyJsonEntity = new AlchemyJsonEntity();
        alchemyJsonEntity.text = "Agile";
        alchemyJsonEntity.language = "english";
        alchemyJsonEntity.type = "development";
        final AlchemyJsonEntity anotherEntity = new AlchemyJsonEntity();
        anotherEntity.text = "Not agile";
        anotherEntity.language = "french";
        anotherEntity.type = "boat";
        result.add(alchemyJsonEntity);
        result.add(anotherEntity);
        return result;
    }

    private void testRelation(final Subject left, final Subject right, final Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private AlchemyJsonEntity simpleEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = new AlchemyJsonEntity();
        alchemyJsonEntity.text = "Agile";
        alchemyJsonEntity.language = "english";
        alchemyJsonEntity.type = "development";
        return alchemyJsonEntity;
    }

    private AlchemyEntityProvider entityProvider;
    private AlchemyEntityAnalyzer analyzer;
}
