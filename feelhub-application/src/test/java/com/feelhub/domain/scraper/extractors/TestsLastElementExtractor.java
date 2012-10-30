package com.feelhub.domain.scraper.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLastElementExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        lastElementExtractor = new LastElementExtractor("h1", "h1");
    }

    @Test
    public void canGetName() {
        assertThat(lastElementExtractor.getName(), is("h1"));
    }

    @Test
    public void canFindH1Tag() {
        final String uri = internet.uri("lastelementextractor/h1tag");
        final Document document = getDocument(uri);

        final String result = lastElementExtractor.apply(document);

        assertThat(result, is("Second section"));
    }

    @Test
    public void canFindTextFromNestedElement() {
        final String uri = internet.uri("lastelementextractor/bug/lemondefrnested");
        final Document document = getDocument(uri);

        final String result = lastElementExtractor.apply(document);

        assertThat(result, is("h1 text"));
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private LastElementExtractor lastElementExtractor;
}
