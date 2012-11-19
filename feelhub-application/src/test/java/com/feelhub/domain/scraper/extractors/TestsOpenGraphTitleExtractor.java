package com.feelhub.domain.scraper.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsOpenGraphTitleExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        openGraphTitleExtractor = new OpenGraphTitleExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(openGraphTitleExtractor.getName()).isEqualTo("opengraphtitle");
    }

    @Test
    public void canFindOpenGraphTitle() {
        final String uri = internet.uri("opengraphextractor/illustration");
        final Document document = getDocument(uri);

        final String result = openGraphTitleExtractor.apply(document);

        assertThat(result).isEqualTo("opengraphtitle");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private OpenGraphTitleExtractor openGraphTitleExtractor;
}
