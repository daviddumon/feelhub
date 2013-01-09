package com.feelhub.web.search;

import com.feelhub.domain.relation.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.mongolink.domain.criteria.Order;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsRelationSearch extends TestWithMongoRepository {

    @Before
    public void before() {
        relationSearch = new RelationSearch(getProvider());
    }

    @Test
    public void canGetARelation() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(from.getId(), to.getId());

        final List<Relation> relations = relationSearch.execute();

        assertThat(relations.size()).isEqualTo(1);
    }

    @Test
    public void canGetARelationForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(from.getId(), to.getId());
        TestFactories.relations().newRelatedList(10);

        final List<Relation> relations = relationSearch.withTopicId(from.getId()).execute();

        assertThat(relations.size()).isEqualTo(1);
        assertThat(relations.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(relations.get(0).getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetRelationsForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelatedList(10, from.getId());
        TestFactories.relations().newRelatedList(20);

        final List<Relation> relations = relationSearch.withTopicId(from.getId()).execute();

        assertThat(relations.size()).isEqualTo(10);
    }

    @Test
    public void canLimitResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelatedList(20);
        TestFactories.relations().newRelatedList(10, from.getId());

        final List<Relation> relations = relationSearch.withTopicId(from.getId()).withLimit(5).execute();

        assertThat(relations.size()).isEqualTo(5);
        assertThat(relations.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(relations.get(1).getFromId()).isEqualTo(from.getId());
        assertThat(relations.get(2).getFromId()).isEqualTo(from.getId());
        assertThat(relations.get(3).getFromId()).isEqualTo(from.getId());
        assertThat(relations.get(4).getFromId()).isEqualTo(from.getId());
    }

    @Test
    @Ignore
    public void canOrderWithWeight() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelatedList(5, from.getId());

        final List<Relation> relations = relationSearch.withSort("weight", Order.DESCENDING).execute();

        assertThat(relations.size()).isEqualTo(5);
        assertThat(relations.get(0).getWeight()).isEqualTo(4.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relations.get(2).getWeight()).isEqualTo(2.0);
        assertThat(relations.get(3).getWeight()).isEqualTo(1.0);
        assertThat(relations.get(4).getWeight()).isEqualTo(0.0);
    }

    @Test
    public void canSkipResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelatedList(5, from.getId());

        final List<Relation> relations = relationSearch.withSkip(2).execute();

        assertThat(relations.size()).isEqualTo(3);
        assertThat(relations.get(0).getWeight()).isEqualTo(2.0);
        assertThat(relations.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relations.get(2).getWeight()).isEqualTo(4.0);
    }

    @Test
    public void canFilterWithDiscriminator() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelated(from.getId(), to.getId());
        final Media media = TestFactories.relations().newMedia(from.getId(), to.getId());

        final List<Relation> relations = relationSearch.withDiscriminator("Media").execute();

        assertThat(relations.size()).isEqualTo(1);
        assertThat(relations.get(0)).isEqualTo(media);
    }

    private RelationSearch relationSearch;
}
