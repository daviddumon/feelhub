package com.feelhub.application.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicSearchTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicSearch = injector.getInstance(TopicSearch.class);
    }

    @Test
    public void canLookupCurrentTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic currentTopic = TestFactories.topics().newCompleteHttpTopic();
        httpTopic.changeCurrentId(currentTopic.getId());
        Repositories.topics().add(httpTopic);
        Repositories.topics().add(currentTopic);

        final Topic topic = topicSearch.lookUpCurrent(httpTopic.getId());

        assertThat(topic).isEqualTo(currentTopic);
    }


    @Test
    public void canGetTopicsFromString() {
        final String value = "tag";
        final Tag tag = TestFactories.tags().newTagWithoutTopic(value);
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());

        final List<Topic> topics = topicSearch.findTopics(value, FeelhubLanguage.reference());

        assertThat(topics.size()).isEqualTo(3);
    }

    @Test
    public void canGetAllHttpTopics() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        new TopicIndexer(httpTopic).index("value", FeelhubLanguage.REFERENCE);
        httpTopic.setUserId(user.getId());

        final List<Topic> topics = topicSearch.findTopics("value", FeelhubLanguage.fromCode("fr"));

        assertThat(topics.size()).isEqualTo(1);
    }


    private TopicSearch topicSearch;
}
