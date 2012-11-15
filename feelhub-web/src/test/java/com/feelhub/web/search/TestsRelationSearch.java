package com.feelhub.web.search;

import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.relation.Relation;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;


public class TestsRelationSearch extends TestWithMongoRepository {

    @Before
    public void before() {
        relationSearch = new RelationSearch(getProvider());
    }

    @Test
    public void canGetARelation() {
        final Word from = TestFactories.keywords().newWord();
        final Word to = TestFactories.keywords().newWord();
        TestFactories.relations().newRelation(from.getTopicId(), to.getTopicId());

        final List<Relation> relations = relationSearch.execute();

        assertThat(relations.size()).isEqualTo(1);
    }

    @Test
    public void canGetARelationForATopic() {
        final Word from = TestFactories.keywords().newWord();
        final Word to = TestFactories.keywords().newWord();
        TestFactories.relations().newRelation(from.getTopicId(), to.getTopicId());
        TestFactories.relations().newRelations(10);

        final List<Relation> relations = relationSearch.withTopicId(from.getTopicId()).execute();

        assertThat(relations.size()).isEqualTo(1);
        assertThat(relations.get(0).getFromId()).isEqualTo(from.getTopicId());
        assertThat(relations.get(0).getToId()).isEqualTo(to.getTopicId());
    }

    @Test
    public void canGetRelationsForATopic() {
        final Word from = TestFactories.keywords().newWord();
        TestFactories.relations().newRelations(10, from.getTopicId());
        TestFactories.relations().newRelations(20);

        final List<Relation> relations = relationSearch.withTopicId(from.getTopicId()).execute();

        assertThat(relations.size()).isEqualTo(10);
    }

    @Test
    public void canLimitResults() {
        final Word from = TestFactories.keywords().newWord();
        TestFactories.relations().newRelations(20);
        TestFactories.relations().newRelations(10, from.getTopicId());

        final List<Relation> relations = relationSearch.withTopicId(from.getTopicId()).withLimit(5).execute();

        assertThat(relations.size()).isEqualTo(5);
        assertThat(relations.get(0).getFromId()).isEqualTo(from.getTopicId());
        assertThat(relations.get(1).getFromId()).isEqualTo(from.getTopicId());
        assertThat(relations.get(2).getFromId()).isEqualTo(from.getTopicId());
        assertThat(relations.get(3).getFromId()).isEqualTo(from.getTopicId());
        assertThat(relations.get(4).getFromId()).isEqualTo(from.getTopicId());
    }

    @Test
    @Ignore
    public void canOrderWithWeight() {
        final Word from = TestFactories.keywords().newWord();
        TestFactories.relations().newRelations(5, from.getTopicId());

        final List<Relation> relations = relationSearch.withSort("weight", Search.REVERSE_ORDER).execute();

        assertThat(relations.size()).isEqualTo(5);
        assertThat(relations.get(0).getWeight()).isEqualTo(4.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relations.get(2).getWeight()).isEqualTo(2.0);
        assertThat(relations.get(3).getWeight()).isEqualTo(1.0);
        assertThat(relations.get(4).getWeight()).isEqualTo(0.0);
    }

    @Test
    public void canSkipResults() {
        final Word from = TestFactories.keywords().newWord();
        TestFactories.relations().newRelations(5, from.getTopicId());

        final List<Relation> relations = relationSearch.withSkip(2).execute();

        assertThat(relations.size()).isEqualTo(3);
        assertThat(relations.get(0).getWeight()).isEqualTo(2.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relations.get(2).getWeight()).isEqualTo(4.0);
    }

    private RelationSearch relationSearch;
}
