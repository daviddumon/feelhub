package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class JsoupTitleExtractorTest {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        jsoupTitleExtractor = new JsoupTitleExtractor();
    }

    @Test
    public void canExtractTitleFromJsoupDocument() {
        final String uri = internet.uri("scraper/jsouptitleextractor");
        final Document document = getDocument(uri);

        final String result = jsoupTitleExtractor.parse(document);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Webpage title");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private JsoupTitleExtractor jsoupTitleExtractor;
}
