package com.feelhub.domain.scraper.pageanalyzer.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsFirstElementExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        firstElementExtractor = new FirstElementExtractor("h2", "h2");
    }

    @Test
    public void canGetName() {
        assertThat(firstElementExtractor.getName()).isEqualTo("h2");
    }

    @Test
    public void canFindH2Tag() {
        final String uri = internet.uri("firstelementextractor/h2tag");
        final Document document = getDocument(uri);

        final String result = firstElementExtractor.apply(document);

        assertThat(result).isEqualTo("First h2 section");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private FirstElementExtractor firstElementExtractor;
}