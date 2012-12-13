package com.feelhub.domain.bingsearch;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.relation.BingRelationBinder;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.web.WebTopicType;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.apache.commons.lang.WordUtils;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsBingSearch {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        relationBinder = mock(BingRelationBinder.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BingLink.class).to(FakeBingLink.class);
                bind(SessionProvider.class).to(FakeSessionProvider.class);
                bind(BingRelationBinder.class).toInstance(relationBinder);
                bind(TagIndexer.class).asEagerSingleton();
            }
        });
        bingSearch = injector.getInstance(BingSearch.class);
    }

    @Test
    public void canCreateImageTopicFromIllustration() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
        final Topic image = Repositories.topics().getAll().get(0);
        assertThat(image.getType()).isEqualTo(WebTopicType.Image);
        assertThat(image.getIllustrationLink()).isEqualTo("query Automobilelink");
        assertThat(image.getName(FeelhubLanguage.none())).isEqualTo(WordUtils.capitalizeFully("query Automobilelink"));
        assertThat(image.getUrls()).contains("query Automobilelink");
    }

    @Test
    public void createRelationsBetweenTopicAndImages() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        verify(relationBinder).bind(any(Topic.class), anyList());
    }

    @Test
    public void topicHasAnIllustrationLink() {
        final RealTopic realTopic = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        assertThat(realTopic.getIllustrationLink()).isEqualTo("query Automobilelink");
    }

    @Test
    public void imageHasTagsOnTopic() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        final Tag tag = Repositories.tags().get("query");
        assertThat(tag.getTopicIds().size()).isEqualTo(1);
    }

    private BingSearch bingSearch;
    private BingRelationBinder relationBinder;
}
