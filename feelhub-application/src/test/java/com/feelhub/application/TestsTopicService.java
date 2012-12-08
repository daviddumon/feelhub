package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.usable.real.*;
import com.feelhub.domain.topic.usable.web.WebTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

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
    public void canCreateATopic() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");

        final RealTopic realTopic = topicService.createTopic(FeelhubLanguage.REFERENCE, "name", RealTopicType.Automobile, fakeActiveUser);

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Name");
    }

    @Test
    public void canLookupTopic() {
        final WebTopic webTopic = TestFactories.topics().newCompleteWebTopic();
        Repositories.topics().add(webTopic);

        final Topic topic = topicService.lookUp(webTopic.getId());

        assertThat(topic).isEqualTo(webTopic);
    }

    private Injector injector;
    private TopicService topicService;
}
