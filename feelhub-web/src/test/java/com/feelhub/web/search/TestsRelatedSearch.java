package com.feelhub.web.search;

import com.feelhub.domain.related.Related;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.mongolink.domain.criteria.Order;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsRelatedSearch extends TestWithMongoRepository {

    @Before
    public void before() {
        relatedSearch = new RelatedSearch(getProvider());
    }

    @Test
    public void canGetARelated() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(from.getId(), to.getId());

        final List<Related> relatedList = relatedSearch.execute();

        assertThat(relatedList.size()).isEqualTo(1);
    }

    @Test
    public void canGetARelatedForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        final RealTopic to = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelated(from.getId(), to.getId());
        TestFactories.related().newRelatedList(10);

        final List<Related> relatedList = relatedSearch.withTopicId(from.getId()).execute();

        assertThat(relatedList.size()).isEqualTo(1);
        assertThat(relatedList.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(relatedList.get(0).getToId()).isEqualTo(to.getId());
    }

    @Test
    public void canGetRelatedForATopic() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(10, from.getId());
        TestFactories.related().newRelatedList(20);

        final List<Related> relatedList = relatedSearch.withTopicId(from.getId()).execute();

        assertThat(relatedList.size()).isEqualTo(10);
    }

    @Test
    public void canLimitResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(20);
        TestFactories.related().newRelatedList(10, from.getId());

        final List<Related> relatedList = relatedSearch.withTopicId(from.getId()).withLimit(5).execute();

        assertThat(relatedList.size()).isEqualTo(5);
        assertThat(relatedList.get(0).getFromId()).isEqualTo(from.getId());
        assertThat(relatedList.get(1).getFromId()).isEqualTo(from.getId());
        assertThat(relatedList.get(2).getFromId()).isEqualTo(from.getId());
        assertThat(relatedList.get(3).getFromId()).isEqualTo(from.getId());
        assertThat(relatedList.get(4).getFromId()).isEqualTo(from.getId());
    }

    @Test
    @Ignore
    public void canOrderWithWeight() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(5, from.getId());

        final List<Related> relatedList = relatedSearch.withSort("weight", Order.DESCENDING).execute();

        assertThat(relatedList.size()).isEqualTo(5);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(4.0);
        assertThat(relatedList.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relatedList.get(2).getWeight()).isEqualTo(2.0);
        assertThat(relatedList.get(3).getWeight()).isEqualTo(1.0);
        assertThat(relatedList.get(4).getWeight()).isEqualTo(0.0);
    }

    @Test
    public void canSkipResults() {
        final RealTopic from = TestFactories.topics().newCompleteRealTopic();
        TestFactories.related().newRelatedList(5, from.getId());

        final List<Related> relatedList = relatedSearch.withSkip(2).execute();

        assertThat(relatedList.size()).isEqualTo(3);
        assertThat(relatedList.get(0).getWeight()).isEqualTo(2.0);
        assertThat(relatedList.get(1).getWeight()).isEqualTo(3.0);
        assertThat(relatedList.get(2).getWeight()).isEqualTo(4.0);
    }

    private RelatedSearch relatedSearch;
}
