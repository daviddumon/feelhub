package com.steambeat.domain.scrapers.extractor;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsLastElementExtractor {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        lastElementExtractor = new LastElementExtractor("h1", "h1");
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canGetName() {
        assertThat(lastElementExtractor.getName(), is("h1"));
    }

    @Test
    public void canFindH1Tag() {
        final Uri uri = internet.uri("http://webscraper/h1tag");
        final Document document = getDocument(uri);

        final String result = lastElementExtractor.apply(document);

        assertThat(result, is("Second section"));
    }

    @Test
    public void canFindTextFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/bug/lemondefrnested");
        final Document document = getDocument(uri);

        final String result = lastElementExtractor.apply(document);

        assertThat(result, is("h1 text"));
    }

    private Document getDocument(final Uri uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private LastElementExtractor lastElementExtractor;
}
