package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsTopicService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicService = injector.getInstance(TopicService.class);
    }

    @Test
    public void canCreateARealTopic() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");

        final RealTopic realTopic = topicService.createRealTopic(FeelhubLanguage.REFERENCE, "name", RealTopicType.Automobile, fakeActiveUser);

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Name");
    }

    @Test
    public void canLookupTopic() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();
        Repositories.topics().add(httpTopic);

        final Topic topic = topicService.lookUp(httpTopic.getId());

        assertThat(topic).isEqualTo(httpTopic);
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
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());
        tag.addTopic(TestFactories.topics().newCompleteRealTopic());

        final List<Topic> topics = topicService.getTopics(value);

        assertThat(topics.size()).isEqualTo(3);
    }

    private Injector injector;
    private TopicService topicService;
}
