package com.steambeat.domain.textAnalytics;

import com.google.common.collect.Lists;
import com.steambeat.domain.subject.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
        WebPage webpage = TestFactories.webPages().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(Lists.newArrayList(simpleNamedEntity()));

        analyzer.analyze(webpage);

        final List<Concept> concepts = Repositories.concepts().getAll();
        assertThat(concepts.size(), is(1));
        assertThat(concepts.get(0).getText(), is("Agile"));
    }

    @Test
    public void createRelationsBetweenConceptsAndPages() {
        WebPage webpage = TestFactories.webPages().newWebPage();
        when(entityProvider.entitiesFor(webpage)).thenReturn(Lists.newArrayList(simpleNamedEntity()));

        analyzer.analyze(webpage);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(2));
        final Concept concept = Repositories.concepts().getAll().get(0);
        testRelation(webpage, concept, relations.get(0));
        testRelation(concept, webpage, relations.get(1));
    }

    private void testRelation(Subject left, Subject right, Relation relation) {
        assertThat(relation.getLeft(), is(left));
        assertThat(relation.getRight(), is(right));
    }

    private NamedEntity simpleNamedEntity() {
        final NamedEntity namedEntity = new NamedEntity();
        namedEntity.text = "Agile";
        namedEntity.language = "english";
        namedEntity.type = "development";
        return namedEntity;
    }

    private NamedEntityProvider entityProvider;
    private NamedEntityAnalyzer analyzer;
}