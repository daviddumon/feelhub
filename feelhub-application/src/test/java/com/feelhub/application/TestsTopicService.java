package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.user.User;
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

        final Topic topic = topicService.createTopic(FeelhubLanguage.REFERENCE, "description", TopicType.Audio, fakeActiveUser);

        assertThat(topic).isNotNull();
        assertThat(topic.getUserId()).isEqualTo(fakeActiveUser.getId());
        assertThat(topic.getDescription(FeelhubLanguage.REFERENCE)).isEqualTo("description");
    }

    @Test
    @Ignore
    public void createTopicFromUriRequestAlchemyAnalysis() {
        //bus.capture(AlchemyRequestEvent.class);
        //final String uri = "http://www.test.com";
        //
        //topicService.createTopicFromUri(uri);
        //
        //final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        //assertThat(alchemyRequestEvent).isNotNull();
        //assertThat(alchemyRequestEvent.getUri().getTopicId()).isEqualTo(uriCreated.getTopicId());
    }

    private Injector injector;
    private TopicService topicService;
}
