package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUriScraper {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canFindSimpleDescriptionFromTitle() {
        final Uri uri = internet.uri("titleextractor/titletag");
        UriScraper uriScraper = new UriScraper(uri);

        uriScraper.scrap();

        assertThat(uriScraper.getDescription(), is("Webpage title"));
    }

    @Test
    public void hasDefaultValuesIfBadUri() {
        final Uri uri = internet.uri("unknown");
        UriScraper uriScraper = new UriScraper(uri);

        uriScraper.scrap();

        assertThat(uriScraper.getDescription(), is("http://localhost:6162/unknown"));
        assertThat(uriScraper.getShortDescription(), is("http://localhost:6162/unknown"));
        assertThat(uriScraper.getIllustration(), is(""));
    }

    @Test
    public void canFindShortDescription() {
        UriScraper uriScraper1 = new UriScraper(internet.uri("titleextractor/titletag"));
        UriScraper uriScraper2 = new UriScraper(internet.uri("lastelementextractor/h1tag"));
        UriScraper uriScraper3 = new UriScraper(internet.uri("logoextractor/withclasslogo"));

        uriScraper1.scrap();
        assertThat(uriScraper1.getShortDescription(), is("Webpage title"));

        uriScraper2.scrap();
        assertThat(uriScraper2.getShortDescription(), is("Second section"));

        uriScraper3.scrap();
        assertThat(uriScraper3.getShortDescription(), is("localhost:6162 [...] hclasslogo"));
    }

    @Test
    public void firstLevelDomainIllustrationPriorityIsLogoThenImage() {
        final Uri uri = internet.uri("/");
        UriScraper uriScraper = new UriScraper(uri);

        uriScraper.scrap();

        assertThat(uri.isFirstLevelUri(), is(true));
        assertThat(uriScraper.getIllustration(), is("http://localhost:6162/logo.jpg"));
    }

    @Test
    public void nonFirstLevelDomainIllustrationPriorityIsImageThenLogo() {
        final Uri uri = internet.uri("uriscraper/logopriority");
        UriScraper uriScraper = new UriScraper(uri);

        uriScraper.scrap();

        assertThat(uri.isFirstLevelUri(), is(false));
        assertThat(uriScraper.getIllustration(), is("http://localhost:6162/uriscraper/image.jpg"));
    }
}
