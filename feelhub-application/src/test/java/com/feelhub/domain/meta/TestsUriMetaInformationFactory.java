package com.feelhub.domain.meta;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.scraper.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsUriMetaInformationFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Scraper.class).to(FakeScraper.class);
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        injector.getInstance(UriMetaInformationFactory.class);
    }

    @Test
    public void canCreateIllustration() {
        final Topic topic = TestFactories.topics().newTopic();
        final UriMetaInformationRequestEvent uriMetaInformationRequestEvent = new UriMetaInformationRequestEvent(topic, "value");

        DomainEventBus.INSTANCE.post(uriMetaInformationRequestEvent);

        final List<Illustration> illustrations = Repositories.illustrations().getAll();
        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0).getTopicId()).isEqualTo(uriMetaInformationRequestEvent.getTopic().getId());
        assertThat(illustrations.get(0).getLink()).isEqualTo("fakeillustration");
    }

    @Test
    public void canUseScrapedInformations() {
        final Topic topic = TestFactories.topics().newTopic();
        final UriMetaInformationRequestEvent uriMetaInformationRequestEvent = new UriMetaInformationRequestEvent(topic, "value");

        DomainEventBus.INSTANCE.post(uriMetaInformationRequestEvent);

        assertThat(topic.getType()).isEqualTo(TopicType.Video);
        assertThat(topic.getDescription(FeelhubLanguage.none())).isEqualTo("faketitle");
    }
}
