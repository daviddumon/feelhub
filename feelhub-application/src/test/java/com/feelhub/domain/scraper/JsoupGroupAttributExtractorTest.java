package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.*;

public class JsoupGroupAttributExtractorTest {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        jsoupGroupAttributExtractor = new JsoupGroupAttributExtractor();
    }

    @Test
    public void canExtractMetaDescription() {
        final String uri = internet.uri("scraper");
        final Document document = getDocument(uri);

        final List<String> result = jsoupGroupAttributExtractor.parse(document, "meta[property=og:image]", "content");

        assertThat(result.size()).isEqualTo(5);
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private JsoupGroupAttributExtractor jsoupGroupAttributExtractor;
}
