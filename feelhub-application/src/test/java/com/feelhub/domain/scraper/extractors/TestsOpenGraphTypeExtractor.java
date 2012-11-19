package com.feelhub.domain.scraper.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsOpenGraphTypeExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        openGraphTypeExtractor = new OpenGraphTypeExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(openGraphTypeExtractor.getName()).isEqualTo("opengraphtype");
    }

    @Test
    public void canFindOpenGraphType() {
        final String uri = internet.uri("opengraphextractor/illustration");
        final Document document = getDocument(uri);

        final String result = openGraphTypeExtractor.apply(document);

        assertThat(result).isEqualTo("video");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private OpenGraphTypeExtractor openGraphTypeExtractor;
}
