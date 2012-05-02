package com.steambeat.web.search;

import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.subject.concept.Concept;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationSearch extends TestWithMongoRepository {

    @Before
    public void before() {
        relationSearch = new RelationSearch(getProvider());
    }

    @Test
    public void canGetARelation() {
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();
        TestFactories.relations().newRelation(from, to);

        final List<Relation> relations = relationSearch.execute();

        assertThat(relations.size(), is(1));
    }

    @Test
    public void canGetARelationForASubject() {
        final WebPage from = TestFactories.subjects().newWebPage();
        final Concept to = TestFactories.subjects().newConcept();
        TestFactories.relations().newRelation(from, to);
        TestFactories.relations().newRelations(10);

        final List<Relation> relations = relationSearch.withFrom(from).execute();

        assertThat(relations.size(), is(1));
        assertThat(relations.get(0).getFromId(), is(from.getId()));
        assertThat(relations.get(0).getToId(), is(to.getId()));
    }

    @Test
    public void canGetRelationsForASubject() {
        final WebPage from = TestFactories.subjects().newWebPage();
        TestFactories.relations().newRelations(10, from);
        TestFactories.relations().newRelations(20);

        final List<Relation> relations = relationSearch.withFrom(from).execute();

        assertThat(relations.size(), is(10));
    }

    @Test
    public void canLimitResults() {
        final WebPage from = TestFactories.subjects().newWebPage();
        TestFactories.relations().newRelations(10, from);
        TestFactories.relations().newRelations(20);

        final List<Relation> relations = relationSearch.withFrom(from).withLimit(5).execute();

        assertThat(relations.size(), is(5));
    }

    @Test
    @Ignore
    public void canOrderWithWeight() {
        final WebPage from = TestFactories.subjects().newWebPage();
        TestFactories.relations().newRelations(5, from);

        final List<Relation> relations = relationSearch.withSort("weight", Search.REVERSE_ORDER).execute();

        assertThat(relations.size(), is(5));
        assertThat(relations.get(0).getWeight(), is(4.0));
        assertThat(relations.get(1).getWeight(), is(3.0));
        assertThat(relations.get(2).getWeight(), is(2.0));
        assertThat(relations.get(3).getWeight(), is(1.0));
        assertThat(relations.get(4).getWeight(), is(0.0));
    }

    @Test
    public void canSkipResults() {
        final WebPage from = TestFactories.subjects().newWebPage();
        TestFactories.relations().newRelations(5, from);

        final List<Relation> relations = relationSearch.withSkip(2).execute();

        assertThat(relations.size(), is(3));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(3.0));
        assertThat(relations.get(2).getWeight(), is(4.0));
    }


    private RelationSearch relationSearch;
}
