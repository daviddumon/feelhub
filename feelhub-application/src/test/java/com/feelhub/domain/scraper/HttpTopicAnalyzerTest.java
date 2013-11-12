package com.feelhub.domain.scraper;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.ThumbnailCreatedEvent;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class HttpTopicAnalyzerTest {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

            }
        });
        httpTopicAnalyzer = injector.getInstance(HttpTopicAnalyzer.class);
    }

    @Test
    public void canSetDescriptionFromScraping() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        assertThat(httpTopic.getDescriptions().size()).isEqualTo(1);
        assertThat(httpTopic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo("description og");
    }

    @Test
    public void canSetNameFromScraping() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        assertThat(httpTopic.getNames().size()).isEqualTo(1);
        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("name og");
    }

    @Test
    public void setCanonicalIfNoName() {
        final String uri = internet.uri("scraper/empty");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        assertThat(httpTopic.getNames().size()).isEqualTo(1);
        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo(uri);
    }

    @Test
    public void canSetHttpTopicType() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        assertThat(httpTopic.getType()).isEqualTo(HttpTopicType.Article);
        assertThat(httpTopic.getOpenGraphType()).isEqualTo("article");
    }

    @Test
    public void canPostThumbnailEventForWebsiteHttpTopicType() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        final ThumbnailCreatedEvent thumbnailCreatedEvent = bus.lastEvent(ThumbnailCreatedEvent.class);
        assertThat(thumbnailCreatedEvent).isNotNull();
        assertThat(thumbnailCreatedEvent.getThumbnail().getOrigin()).isEqualTo("http://s1.lemde.fr/image/2013/01/25/540x270/1822831_3_dfb7_un-manifestant-lance-un-cocktail-molotov-contre_ed5d9c3af6a609128210a9cab7111290.jpg");
    }

    @Test
    public void canPostThumbnailEventForImageHttpTopicType() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Image);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        final ThumbnailCreatedEvent thumbnailCreatedEvent = bus.lastEvent(ThumbnailCreatedEvent.class);
        assertThat(thumbnailCreatedEvent).isNotNull();
        assertThat(thumbnailCreatedEvent.getThumbnail().getOrigin()).isEqualTo("http://localhost:6162/scraper");
    }

    @Test
    public void canPostThumbnailEventForDataHttpTopicType() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Data);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic.getId());

        final ThumbnailCreatedEvent thumbnailCreatedEvent = bus.lastEvent(ThumbnailCreatedEvent.class);
        assertThat(thumbnailCreatedEvent).isNotNull();
        assertThat(thumbnailCreatedEvent.getThumbnail().getOrigin()).isEqualTo("http://localhost:6162/scraper");
    }

    private HttpTopicAnalyzer httpTopicAnalyzer;
}
