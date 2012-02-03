package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsWebPageScraper {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        webPageScraper = new WebPageScraper();
    }

    @Test
    public void canScrapAnUriWithoutTitle() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithouttitle");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is(""));
    }

    @Test
    public void canScrapTitle() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithtitle");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is("Webpage title"));
    }

    @Test
    public void canScrapTitleBadHtml() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithtitleandbadhtml");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is("Webpage title"));
    }

    @Test
    public void canScrapFirstH1Section() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithh1section");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("h1"), is("First section"));
    }

    @Test
    public void canScrapFirstH2Section() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithh2section");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("h2"), is("First h2 section"));
    }

    @Test
    public void canScrapFirstH3Section() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithh3section");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("h3"), is("First h3 section"));
    }

    @Test
    public void canScrapFirstImageUrl() {
        final Uri uri = internet.uri("http://webscraper/fakesimpleuriwithimage");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("firstImageUrl"), is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void scraperHasATimeout() {
        expectedException.expect(WebScraperException.class);
        final Uri uri = internet.uri("http://webscraper/fakesimpleurinonresponsive");

        webPageScraper.scrapDocument(uri);
    }

    @Test
    public void canScrapImageUrlWithClassContainingLogo() {
        final Uri uri = internet.uri("http://webscraper/logo/imgClassLogo");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapImageUrlWithClassContainingLogoPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/imgClassLogoPattern");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapImageUrlWithIdContainingLogo() {
        final Uri uri = internet.uri("http://webscraper/logo/imgIdLogo");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapImageUrlWithIdContainingLogoPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/imgIdLogoPattern");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapImageUrlWithAltContainingLogo() {
        final Uri uri = internet.uri("http://webscraper/logo/imgAltLogo");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapImageUrlWithAltContainingLogoPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/imgAltLogoPattern");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapBackgroundImageUrl() {
        final Uri uri = internet.uri("http://webscraper/logo/imgSimpleBackground");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.logo.com/logo.jpg"));
    }

    @Test
    public void canScrapLogoUrlFromNestedChildrenTags() {
        final Uri uri = internet.uri("http://webscraper/logo/imgNestedChildren");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canScrapLogoUrlFromParentTags() {
        final Uri uri = internet.uri("http://webscraper/logo/imgParent");

        webPageScraper.scrapDocument(uri);

        assertThat(webPageScraper.getScrapedTags().get("logoUrl"), is("http://www.google.fr/images/lol.jpg"));
    }

    private WebPageScraper webPageScraper;
}
