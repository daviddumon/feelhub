package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.tools.*;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicServiceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        final String canonicalUri = "http://www.urlcanonique.com";
        fakeUriResolver.thatFind(canonicalUri);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).toInstance(fakeUriResolver);
                bind(Scraper.class).toInstance(new FakeScraper());
                bind(MongoLinkAwareExecutor.class).to(FakeMongoLinkAwareExecutor.class);
                bind(HttpTopicAnalyzer.class).asEagerSingleton();
            }
        });
        topicService = injector.getInstance(TopicService.class);
    }

    @Test
    public void canLookupCurrentTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        final HttpTopic currentTopic = TestFactories.topics().newCompleteHttpTopic();
        httpTopic.changeCurrentId(currentTopic.getId());
        Repositories.topics().add(httpTopic);
        Repositories.topics().add(currentTopic);

        final Topic topic = topicService.lookUpCurrent(httpTopic.getId());

        assertThat(topic).isEqualTo(currentTopic);
    }

    @Test
    public void canGetTopicsFromString() {
        final String value = "tag";
        final Tag tag = TestFactories.tags().newTagWithoutTopic(value);
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic(), FeelhubLanguage.none());

        final List<Topic> topics = topicService.getTopics(value, FeelhubLanguage.reference());

        assertThat(topics.size()).isEqualTo(3);
    }

    @Test
    public void canGetAllHttpTopics() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        new TopicIndexer(httpTopic).index("value", FeelhubLanguage.REFERENCE);
        httpTopic.setUserId(user.getId());

        final List<Topic> topics = topicService.getTopics("value", FeelhubLanguage.fromCode("fr"));

        assertThat(topics.size()).isEqualTo(1);
    }

    private TopicService topicService;
}