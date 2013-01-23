package com.feelhub.domain.scraper;

import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

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
            }
        });
        httpTopicAnalyzer = injector.getInstance(HttpTopicAnalyzer.class);
    }

    @Test
    public void canUseHttpTopicUriForScraping() {
        final String uri = internet.uri("scraper/httptopicanalyzer1");
        final HttpTopic httpTopic = TestFactories.topics().newSimpleHttpTopic(HttpTopicType.Website);
        httpTopic.addUri(new Uri(uri));

        httpTopicAnalyzer.analyze(httpTopic);

        assertThat(httpTopic.getDescriptions().size()).isEqualTo(1);
    }

    private HttpTopicAnalyzer httpTopicAnalyzer;
    private Injector injector;
}
