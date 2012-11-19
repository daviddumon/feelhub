package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.google.inject.*;
import org.junit.*;

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
