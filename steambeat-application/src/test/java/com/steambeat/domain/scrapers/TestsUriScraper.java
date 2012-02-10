package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUriScraper {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        uriScraper = new UriScraper();
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canFindSimpleDescriptionFromTitle() {
        final Uri uri = internet.uri("http://webscraper/titletag");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("Webpage title"));
    }

    @Test
    public void hasDefaultValuesIfBadUri() {
        final Uri uri = internet.uri("unknown");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("http://localhost:6162/unknown"));
        assertThat(uriScraper.getShortDescription(), is("http://localhost:6162/unknown"));
        assertThat(uriScraper.getIllustration(), is(""));
    }

    @Test
    public void canFindShortDescription() {
        final Uri uri1 = internet.uri("http://webscraper/titletag");
        final Uri uri2 = internet.uri("http://webscraper/h1tag");
        final Uri uri3 = internet.uri("http://webscraper/logo/withclasslogo");

        uriScraper.scrap(uri1);
        assertThat(uriScraper.getShortDescription(), is("Webpage title"));

        uriScraper.scrap(uri2);
        assertThat(uriScraper.getShortDescription(), is("Second section"));

        uriScraper.scrap(uri3);
        assertThat(uriScraper.getShortDescription(), is("webscraper [...] hclasslogo"));
    }

    private UriScraper uriScraper;
}
