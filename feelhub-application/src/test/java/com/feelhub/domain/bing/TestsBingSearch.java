package com.feelhub.domain.bing;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.MediaType;

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
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        fakeUriResolver.thatFind(MediaType.IMAGE_JPEG);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BingLink.class).to(FakeBingLink.class);
                bind(SessionProvider.class).to(FakeSessionProvider.class);
                bind(BingRelationBinder.class).toInstance(relationBinder);
                bind(UriResolver.class).toInstance(fakeUriResolver);
            }
        });
        bingSearch = injector.getInstance(BingSearch.class);
    }

    @Test
    public void canCreateImageTopicFromIllustration() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopicId(realTopic.getId());
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        assertThat(Repositories.topics().getAll().size()).isEqualTo(2);
        final Topic image = Repositories.topics().getAll().get(1);
        assertThat(image.getType()).isEqualTo(HttpTopicType.Image);
        assertThat(image.getIllustration()).isEqualTo("query Automobilelink");
        assertThat(image.getUris()).contains(new Uri("query Automobilelink"));
    }

    @Test
    public void createRelationsBetweenTopicAndImages() {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        Repositories.topics().add(realTopic);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopicId(realTopic.getId());
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        verify(relationBinder).bind(any(Topic.class), anyList());
    }

    @Test
    public void topicHasAnIllustration() {
        final RealTopic realTopic = TestFactories.topics().newSimpleRealTopic(RealTopicType.Automobile);
        final BingRequest bingRequest = new BingRequest();
        bingRequest.setTopicId(realTopic.getId());
        bingRequest.setTopic(realTopic);
        bingRequest.setQuery("query");

        DomainEventBus.INSTANCE.post(bingRequest);

        assertThat(realTopic.getIllustration()).isEqualTo("query Automobilelink");
    }

    private BingSearch bingSearch;
    private BingRelationBinder relationBinder;
}
