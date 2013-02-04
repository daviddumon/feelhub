package com.feelhub.domain.scraper;

import com.feelhub.domain.cloudinary.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsHttpTopicAnalyzer {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CloudinaryLink.class).to(FakeCloudinaryLink.class);
            }
        });
        httpTopicAnalyzer = injector.getInstance(HttpTopicAnalyzer.class);
    }

    @Test
    public void canSetDescriptionFromScraping() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic);

        assertThat(httpTopic.getDescriptions().size()).isEqualTo(1);
        assertThat(httpTopic.getDescription(FeelhubLanguage.fromCode("fr"))).isEqualTo("description og");
    }

    @Test
    public void canSetNameFromScraping() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic);

        assertThat(httpTopic.getNames().size()).isEqualTo(1);
        assertThat(httpTopic.getName(FeelhubLanguage.fromCode("fr"))).isEqualTo("name og");
    }

    @Test
    public void canSetHttpTopicType() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic);

        assertThat(httpTopic.getType()).isEqualTo(HttpTopicType.Article);
        assertThat(httpTopic.getOpenGraphType()).isEqualTo("article");
    }

    @Test
    @Ignore
    public void canSetIllustrations() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic);

        assertThat(httpTopic.getIllustration()).isEqualTo("http://s1.lemde.fr/image/2013/01/25/540x270/1822831_3_dfb7_un-manifestant-lance-un-cocktail-molotov-contre_ed5d9c3af6a609128210a9cab7111290.jpg");
        assertThat(httpTopic.getThumbnailLarge()).isEqualTo("thumbnail");
        assertThat(httpTopic.getThumbnailMedium()).isEqualTo("thumbnail");
        assertThat(httpTopic.getThumbnailSmall()).isEqualTo("thumbnail");
    }

    @Test
    public void returnMedias() {
        final String uri = internet.uri("scraper");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        final List<String> medias = httpTopicAnalyzer.analyze(httpTopic);

        assertThat(medias.size()).isEqualTo(11);
    }

    private HttpTopicAnalyzer httpTopicAnalyzer;
    private Injector injector;
}
