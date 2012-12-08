package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.meta.UriMetaInformationRequestEvent;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.*;

public class TestsTopicFromUriService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Scraper.class).to(FakeScraper.class);
            }
        });
        topicFromUriService = injector.getInstance(TopicFromUriService.class);
    }

    @Test
    public void canCreateAnUri() {
        final RealTopic realTopic = topicFromUriService.createTopicFromUri("http://www.google.fr");

        assertThat(realTopic).isNotNull();
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

    @Test
    public void requestUriMetaInformationRequestEvent() {
        bus.capture(UriMetaInformationRequestEvent.class);
        final String value = "value";

        final RealTopic realTopic = topicFromUriService.createTopicFromUri(value);

        final UriMetaInformationRequestEvent uriMetaInformationRequestEvent = bus.lastEvent(UriMetaInformationRequestEvent.class);
        assertThat(uriMetaInformationRequestEvent).isNotNull();
        assertThat(uriMetaInformationRequestEvent.getRealTopic()).isEqualTo(realTopic);
        assertThat(uriMetaInformationRequestEvent.getValue()).isEqualTo(value);
    }

    @Test
    public void requestAlchemy() {
        bus.capture(AlchemyRequestEvent.class);
        final String value = "http://www.test.com";

        final RealTopic realTopic = topicFromUriService.createTopicFromUri(value);

        final AlchemyRequestEvent alchemyRequestEvent = bus.lastEvent(AlchemyRequestEvent.class);
        assertThat(alchemyRequestEvent).isNotNull();
        assertThat(alchemyRequestEvent.getRealTopic()).isEqualTo(realTopic);
        assertThat(alchemyRequestEvent.getValue()).isEqualTo(value);
    }

    private TopicFromUriService topicFromUriService;
}
