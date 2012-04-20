package com.steambeat.domain.analytics.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyXmlEntity;
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

public class TestsNamedEntityAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        analyzer = new NamedEntityAnalyzer(entityProvider);
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

    private List<AlchemyXmlEntity> complexNamedEntities() {
        List<AlchemyXmlEntity> result = Lists.newArrayList();
        final AlchemyXmlEntity alchemyXmlEntity = new AlchemyXmlEntity();
        alchemyXmlEntity.text = "Agile";
        alchemyXmlEntity.language = "english";
        alchemyXmlEntity.type = "development";
        final AlchemyXmlEntity anotherEntity = new AlchemyXmlEntity();
        anotherEntity.text = "Not agile";
        anotherEntity.language = "french";
        anotherEntity.type = "boat";
        result.add(alchemyXmlEntity);
        result.add(anotherEntity);
        return result;
    }

    private void testRelation(final Subject left, final Subject right, final Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private AlchemyXmlEntity simpleEntity() {
        final AlchemyXmlEntity alchemyXmlEntity = new AlchemyXmlEntity();
        alchemyXmlEntity.text = "Agile";
        alchemyXmlEntity.language = "english";
        alchemyXmlEntity.type = "development";
        return alchemyXmlEntity;
    }

    private NamedEntityProvider entityProvider;
    private NamedEntityAnalyzer analyzer;
}
