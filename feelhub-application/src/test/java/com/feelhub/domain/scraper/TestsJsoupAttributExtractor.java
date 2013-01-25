package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsJsoupAttributExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        jsoupAttributExtractor = new JsoupAttributExtractor();
    }

    @Test
    public void canExtractMetaDescription() {
        final String uri = internet.uri("scraper/jsoupattributextractor");
        final Document document = getDocument(uri);

        final String result = jsoupAttributExtractor.parse(document, "html", "lang");

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("en");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private JsoupAttributExtractor jsoupAttributExtractor;
}
