package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.jsoup.nodes.Document;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsScraper {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        scraper = new Scraper();
    }

    @Test
    public void returnGoodDocument() {
        final String uri = internet.uri("scraper/logopriority");

        final Document scrap = scraper.scrap(uri);

        assertThat(scrap).isNotNull();
    }

    @Test
    public void returnEmptyDocumentIfError() {
        final String uri = internet.uri("unknown");

        final Document scrap = scraper.scrap(uri);

        assertThat(scrap).isNotNull();
        assertThat(scrap.getAllElements().size()).isEqualTo(1);
    }

    private Scraper scraper;
}
