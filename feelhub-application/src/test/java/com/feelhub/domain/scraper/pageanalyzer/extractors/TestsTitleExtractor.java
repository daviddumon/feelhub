package com.feelhub.domain.scraper.pageanalyzer.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsTitleExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        titleExtractor = new TitleExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(titleExtractor.getName()).isEqualTo("title");
    }

    @Test
    public void canExtractSimpleTitle() {
        final String uri = internet.uri("titleextractor/titletag");
        final Document document = getDocument(uri);

        final String result = titleExtractor.apply(document);

        assertThat(result).isEqualTo("Webpage title");
    }

    @Test
    public void canFindTitleTagWithBadHtml() {
        final String uri = internet.uri("titleextractor/titletagbadhtml");
        final Document document = getDocument(uri);

        final String result = titleExtractor.apply(document);

        assertThat(result).isEqualTo("Webpage title");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private TitleExtractor titleExtractor;
}
