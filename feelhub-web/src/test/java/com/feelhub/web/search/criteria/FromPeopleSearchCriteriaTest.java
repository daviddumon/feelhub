package com.feelhub.web.search.criteria;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.search.TopicSearch;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FromPeopleSearchCriteriaTest extends TestWithMongoRepository {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        topicSearch = new TopicSearch(getProvider());
    }

    @Test
    public void defaultCriteriaIsAll() {
        assertThat(FromPeopleSearchCriteria.defaultValue()).isEqualTo(FromPeopleSearchCriteria.All);
    }

    @Test
    public void canGetValueFromString() {
        assertThat(FromPeopleSearchCriteria.fromString("From me")).isEqualTo(FromPeopleSearchCriteria.My);
        assertThat(FromPeopleSearchCriteria.fromString("From everyone")).isEqualTo(FromPeopleSearchCriteria.All);
    }

    @Test
    public void canGetTopicsFromUser() {
        TestFactories.topics().newCompleteRealTopic();
        final Topic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = FromPeopleSearchCriteria.My.addCriteria(topic.getUserId(), topicSearch).execute();

        assertThat(topicList.size()).isEqualTo(1);
        assertThat(topicList).contains(topic);
    }

    @Test
    public void canGetTopicsFromAllUsersWhereUserIdIsNull() {
        TestFactories.topics().newCompleteRealTopic();
        TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = FromPeopleSearchCriteria.My.addCriteria(null, topicSearch).execute();

        assertThat(topicList.size()).isEqualTo(2);
    }

    @Test
    public void canGetTopicsFromAllUsers() {
        TestFactories.topics().newCompleteRealTopic();
        final Topic topic = TestFactories.topics().newCompleteRealTopic();

        final List<Topic> topicList = FromPeopleSearchCriteria.All.addCriteria(topic.getUserId(), topicSearch).execute();

        assertThat(topicList.size()).isEqualTo(2);
    }

    private TopicSearch topicSearch;
}
