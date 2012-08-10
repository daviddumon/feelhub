package com.steambeat.domain.scrapers.extractors;

import com.steambeat.domain.association.uri.Uri;
import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsTitleExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        titleExtractor = new TitleExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(titleExtractor.getName(), is("title"));
    }

    @Test
    public void canExtractSimpleTitle() {
        final Uri uri = new Uri(internet.uri("titleextractor/titletag"));
        final Document document = getDocument(uri);

        final String result = titleExtractor.apply(document);

        assertThat(result, is("Webpage title"));
    }

    @Test
    public void canFindTitleTagWithBadHtml() {
        final Uri uri = new Uri(internet.uri("titleextractor/titletagbadhtml"));
        final Document document = getDocument(uri);

        final String result = titleExtractor.apply(document);

        assertThat(result, is("Webpage title"));
    }

    private Document getDocument(final Uri uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private TitleExtractor titleExtractor;
}
