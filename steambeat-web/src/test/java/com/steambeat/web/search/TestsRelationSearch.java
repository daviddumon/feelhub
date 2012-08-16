package com.steambeat.web.search;

import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.TestFactories;
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
        final Reference from = TestFactories.references().newReference();
        final Reference to = TestFactories.references().newReference();
        TestFactories.relations().newRelation(from, to);

        final List<Relation> relations = relationSearch.execute();

        assertThat(relations.size(), is(1));
    }

    @Test
    public void canGetARelationForASubject() {
        final Reference from = TestFactories.references().newReference();
        final Reference to = TestFactories.references().newReference();
        TestFactories.relations().newRelation(from, to);
        TestFactories.relations().newRelations(10);

        final List<Relation> relations = relationSearch.withFrom(from).execute();

        assertThat(relations.size(), is(1));
        assertThat(relations.get(0).getFromId(), is(from.getId()));
        assertThat(relations.get(0).getToId(), is(to.getId()));
    }

    @Test
    public void canGetRelationsForASubject() {
        final Reference from = TestFactories.references().newReference();
        TestFactories.relations().newRelations(10, from);
        TestFactories.relations().newRelations(20);

        final List<Relation> relations = relationSearch.withFrom(from).execute();

        assertThat(relations.size(), is(10));
    }

    @Test
    public void canLimitResults() {
        final Reference from = TestFactories.references().newReference();
        TestFactories.relations().newRelations(10, from);
        TestFactories.relations().newRelations(20);

        final List<Relation> relations = relationSearch.withFrom(from).withLimit(5).execute();

        assertThat(relations.size(), is(5));
    }

    @Test
    @Ignore
    public void canOrderWithWeight() {
        final Reference from = TestFactories.references().newReference();
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
        final Reference from = TestFactories.references().newReference();
        TestFactories.relations().newRelations(5, from);

        final List<Relation> relations = relationSearch.withSkip(2).execute();

        assertThat(relations.size(), is(3));
        assertThat(relations.get(0).getWeight(), is(2.0));
        assertThat(relations.get(1).getWeight(), is(3.0));
        assertThat(relations.get(2).getWeight(), is(4.0));
    }


    private RelationSearch relationSearch;
}
