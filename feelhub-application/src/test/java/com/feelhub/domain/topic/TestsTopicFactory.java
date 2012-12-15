package com.feelhub.domain.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsTopicFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final FakeUriResolver fakeUriResolver = new FakeUriResolver();
        canonicalUri = "http://www.urlcanonique.com";
        fakeUriResolver.thatFind(canonicalUri);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).toInstance(fakeUriResolver);
                bind(TagIndexer.class).asEagerSingleton();
                bind(SessionProvider.class).to(FakeSessionProvider.class);
            }
        });
        topicFactory = injector.getInstance(TopicFactory.class);
    }

    @Test
    public void canCreateRealTopic() {
        final RealTopic realTopic = topicFactory.createRealTopic(FeelhubLanguage.REFERENCE, "topic", RealTopicType.City);

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getCurrentId()).isEqualTo(realTopic.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Topic");
        assertThat(realTopic.getType()).isEqualTo(RealTopicType.City);
    }

    @Test
    public void canCreateHttpTopic() {
        final HttpTopic httpTopic = topicFactory.createHttpTopic("http://www.url.com");

        assertThat(httpTopic).isNotNull();
        assertThat(httpTopic.getType()).isEqualTo(HttpTopicType.Website);
    }

    @Test
    public void addCanonicalUriForHttpTopic() {
        final HttpTopic httpTopic = topicFactory.createHttpTopic("http://www.url.com");

        assertThat(httpTopic.getUris().size()).isEqualTo(1);
        assertThat(httpTopic.getUris()).contains(new Uri(canonicalUri));
    }

    @Test
    public void createTagsForHttpTopic() {
        topicFactory.createHttpTopic("http://www.url.com");

        final List<Tag> tags = Repositories.tags().getAll();
        assertThat(tags.size()).isEqualTo(8);
    }

    @Test
    public void canCreateWorldTopic() {
        final WorldTopic worldTopic = topicFactory.createWorldTopic();

        assertThat(worldTopic).isNotNull();
    }

    private TopicFactory topicFactory;
    private String canonicalUri;
}