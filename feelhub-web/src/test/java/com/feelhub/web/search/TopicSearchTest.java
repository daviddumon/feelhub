package com.feelhub.web.search;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.junit.*;
import org.mongolink.domain.criteria.Order;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicSearchTest extends TestWithMongoRepository {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        topicSearch = new TopicSearch(getProvider());
    }

    @Test
    public void canGetOneTopic() {
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.execute();

        assertThat(topicList).isNotNull();
        assertThat(topicList.size()).isEqualTo(1);
    }

    @Test
    public void canGetAllTopics() {
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.execute();

        assertThat(topicList.size()).isEqualTo(3);
    }

    @Test
    public void canGetTopicsWithSkip() {
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withSkip(1).execute();

        assertThat(topicList.size()).isEqualTo(2);
    }

    @Test
    public void canGetTopicsWithLimit() {
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withLimit(1).execute();

        assertThat(topicList.size()).isEqualTo(1);
    }

    @Test
    public void canGetTopicsWithSort() {
        final RealTopic topicA = TestFactories.topics().newCompleteRealTopic();
        time.waitDays(1);
        final RealTopic topicB = TestFactories.topics().newCompleteRealTopic();
        time.waitDays(1);
        final RealTopic topicC = TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withSort("creationDate", Order.DESCENDING).execute();

        assertThat(topicList.size()).isEqualTo(3);
        assertThat(topicList.get(0)).isEqualTo(topicC);
        assertThat(topicList.get(1)).isEqualTo(topicB);
        assertThat(topicList.get(2)).isEqualTo(topicA);
    }

    @Test
    public void canGetATopicWithId() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withTopicId(topic.getCurrentId()).execute();

        assertThat(topicList.size()).isEqualTo(1);
        assertThat(topicList.get(0)).isEqualTo(topic);
    }

    @Test
    public void canGetATopicFromCurrentId() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withCurrentId(topic.getCurrentId()).execute();

        assertThat(topicList.size()).isEqualTo(1);
        assertThat(topicList.get(0)).isEqualTo(topic);
    }

    @Test
    public void canGetTopicsWithFeelings() {
        final RealTopic topicA = TestFactories.topics().newCompleteRealTopicWithHasFeelings();
        final RealTopic topicB = TestFactories.topics().newCompleteRealTopicWithHasFeelings();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withFeelings().execute();

        assertThat(topicList.size()).isEqualTo(2);
        assertThat(topicList.get(0)).isEqualTo(topicA);
        assertThat(topicList.get(1)).isEqualTo(topicB);
    }

    @Test
    public void ignoreWorldTopic() {
        TestFactories.topics().newWorldTopic();
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topics = topicSearch.execute();

        assertThat(topics.size()).isEqualTo(1);
    }

    @Test
    public void canGetTopicsFromUserId() {
        TestFactories.topics().newCompleteRealTopic();
        final Topic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = topicSearch.withUserId(topic.getUserId()).execute();

        assertThat(topicList.size()).isEqualTo(1);
        assertThat(topicList).contains(topic);
    }

    @Test
    @Ignore("Mongolink ne supporte pas le FakeCriteria in")
    public void canGetTopicsWithLanguageList() {
        final HttpTopic httpTopicA = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic httpTopicB = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic httpTopicC = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic httpTopicD = TestFactories.topics().newCompleteHttpTopic();
        httpTopicA.setLanguageCode(FeelhubLanguage.none().getCode());
        httpTopicB.setLanguageCode(FeelhubLanguage.fromCode("es").getCode());
        httpTopicC.setLanguageCode(FeelhubLanguage.fromCode("fr").getCode());
        httpTopicD.setLanguageCode(FeelhubLanguage.reference().getCode());

        final List<Topic> topics = topicSearch.withLanguages(Lists.newArrayList(FeelhubLanguage.none(), FeelhubLanguage.reference(), FeelhubLanguage.fromCode("fr"))).execute();

        assertThat(topics.size()).isEqualTo(3);
        assertThat(topics).contains(httpTopicA);
        assertThat(topics).contains(httpTopicB);
        assertThat(topics).contains(httpTopicD);
    }

    private TopicSearch topicSearch;
}
