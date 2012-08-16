package com.steambeat.domain.scrapers.extractors;

import com.steambeat.domain.subject.uri.Uri;
import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFirstElementExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        firstElementExtractor = new FirstElementExtractor("h2", "h2");
    }

    @Test
    public void canGetName() {
        assertThat(firstElementExtractor.getName(), is("h2"));
    }

    @Test
    public void canFindH2Tag() {
        final Uri uri = new Uri(internet.uri("firstelementextractor/h2tag"));
        final Document document = getDocument(uri);

        final String result = firstElementExtractor.apply(document);

        assertThat(result, is("First h2 section"));
    }

    private Document getDocument(final Uri uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private FirstElementExtractor firstElementExtractor;
}
