package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.MediaType;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsTopicFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        canonicalUri = new Uri("http://www.fakeurl.com");
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicFactory = injector.getInstance(TopicFactory.class);
    }

    @Test
    public void canCreateRealTopic() {
        final RealTopic realTopic = topicFactory.createRealTopic(FeelhubLanguage.REFERENCE, "topic", RealTopicType.City, UUID.randomUUID());

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getCurrentId()).isEqualTo(realTopic.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Topic");
        assertThat(realTopic.getType()).isEqualTo(RealTopicType.City);
        assertThat(bus.lastEvent(RealTopicCreatedEvent.class)).isNotNull();
    }

    @Test
    public void canCreateHttpTopic() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(canonicalUri);
        final UriResolver uriResolver = mock(UriResolver.class);
        when(uriResolver.resolve(new Uri("value"))).thenReturn(resolverResult);

        final HttpTopic httpTopic = topicFactory.createHttpTopic("value", UUID.randomUUID(), uriResolver);

        assertThat(httpTopic).isNotNull();
        assertThat(httpTopic.getType()).isEqualTo(HttpTopicType.Website);
    }

    @Test
    public void addCanonicalUriForHttpTopic() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(canonicalUri);
        final UriResolver uriResolver = mock(UriResolver.class);
        when(uriResolver.resolve(new Uri("value"))).thenReturn(resolverResult);

        final HttpTopic httpTopic = topicFactory.createHttpTopic("value", UUID.randomUUID(), uriResolver);

        assertThat(httpTopic.getUris().size()).isEqualTo(1);
        assertThat(httpTopic.getUris()).contains(canonicalUri);
    }

    @Test
    public void postHttpTopicCreatedEvent() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(canonicalUri);
        final UriResolver uriResolver = mock(UriResolver.class);
        when(uriResolver.resolve(new Uri("value"))).thenReturn(resolverResult);

        final HttpTopic topic = topicFactory.createHttpTopic("value", UUID.randomUUID(), uriResolver);

        final HttpTopicCreatedEvent event = bus.lastEvent(HttpTopicCreatedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.topicId).isEqualTo(topic.getId());
        assertThat(event.resolverResult).isEqualTo(resolverResult);
    }

    @Test
    @Ignore("broke because we do not return anymore the topic")
    public void scrapHttpTopicIfWebsite() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(canonicalUri);
        final UriResolver uriResolver = mock(UriResolver.class);
        when(uriResolver.resolve(new Uri("value"))).thenReturn(resolverResult);

        final HttpTopic httpTopic = topicFactory.createHttpTopic("value", UUID.randomUUID(), uriResolver);

        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("name");
    }

    @Test
    @Ignore("broke because we do not return anymore the topic")
    public void scrapOnlyHttpTopicWithGoodMediaFilter() {
        final ResolverResult resolverResult = new ResolverResult();
        resolverResult.setMediaType(MediaType.TEXT_HTML);
        resolverResult.addUriToPath(canonicalUri);
        final UriResolver uriResolver = mock(UriResolver.class);
        when(uriResolver.resolve(new Uri("value"))).thenReturn(resolverResult);

        final HttpTopic httpTopic = topicFactory.createHttpTopic("value", UUID.randomUUID(), uriResolver);

        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("name");
    }

    @Test
    public void canCreateWorldTopic() {
        final WorldTopic worldTopic = topicFactory.createWorldTopic();

        assertThat(worldTopic).isNotNull();
    }

    @Test
    public void canSpecifyRestrictedMimeType() {
        exception.expect(TopicException.class);

        new TopicFactory().createHttpTopicWithMediaType("http://www.url.com", MediaType.IMAGE_ALL, new FakeUriResolver());
    }

    private TopicFactory topicFactory;
    private Uri canonicalUri;

}