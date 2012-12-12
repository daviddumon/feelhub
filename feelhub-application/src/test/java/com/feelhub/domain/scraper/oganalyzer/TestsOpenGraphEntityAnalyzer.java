package com.feelhub.domain.scraper.oganalyzer;

import com.feelhub.domain.scraper.Scraper;
import com.feelhub.domain.topic.usable.web.*;
import com.feelhub.test.FakeInternet;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

@Ignore
public class TestsOpenGraphEntityAnalyzer {

    private OpenGraphEntityAnalyzer openGraphEntityAnalyzer;
    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        openGraphEntityAnalyzer = new OpenGraphEntityAnalyzer();
    }

    @Test
    public void emptyListIfNoOpengraphEntities() {
        final Document document = getDocument("oganalyzer/empty");

        final List<WebTopic> topics = openGraphEntityAnalyzer.analyze(document);

        assertThat(topics).isEmpty();
    }

    @Test
    public void canExtractEntitiesForWebsite() {
        final Document document = getDocument("oganalyzer/website");

        final List<WebTopic> topics = openGraphEntityAnalyzer.analyze(document);

        assertThat(topics.size()).isEqualTo(2);
    }

    @Test
    public void canExtractGoodValues() {
        final Document document = getDocument("oganalyzer/website");

        final List<WebTopic> topics = openGraphEntityAnalyzer.analyze(document);

        final WebTopic webTopic = topics.get(0);
        assertThat(webTopic.getType()).isEqualTo(WebTopicType.Website);

    }

    private Document getDocument(final String value) {
        final Scraper scraper = new Scraper();
        final String uri = internet.uri(value);
        return scraper.scrap(uri);
    }
}
