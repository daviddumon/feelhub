package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsJsoupTagExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        jsoupTagExtractor = new JsoupTagExtractor();
    }

    @Test
    public void canExtractATagFromJsoupDocument() {
        final String uri = internet.uri("scraper/jsouptagextractor");
        final Document document = getDocument(uri);

        final String result = jsoupTagExtractor.parse(document, "h2");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("First h2 section");
    }

    @Test
    public void canFindTextFromNestedElement() {
        final String uri = internet.uri("scraper/jsouptagextractor");
        final Document document = getDocument(uri);

        final String result = jsoupTagExtractor.parse(document, "h1");

        assertThat(result).isEqualTo("h1 nested text");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private JsoupTagExtractor jsoupTagExtractor;
}
