package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsUriScraper {

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        uriScraper = new UriScraper();
    }

    @Test
    public void canFindTitleTag() {
        final Uri uri = internet.uri("http://webscraper/titletag");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("Webpage title"));
    }

    @Test
    public void continueIfBadUri() {
        final Uri uri = internet.uri("unknown");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is(""));
        assertThat(uriScraper.getShortDescription(), is("localhost:6162/unknown"));
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

    @Test
    public void canFindTitleTagWithBadHtml() {
        final Uri uri = internet.uri("http://webscraper/titletagbadhtml");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("Webpage title"));
    }

    @Test
    public void canFindH1Tag() {
        final Uri uri = internet.uri("http://webscraper/h1tag");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("Second section"));
    }

    @Test
    public void canFindH2Tag() {
        final Uri uri = internet.uri("http://webscraper/h2tag");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("First h2 section"));
    }

    @Test
    public void canFindLogoWithClass() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogo");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithId() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogo");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogo");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogopattern");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogopattern");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogopattern");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final Uri uri = internet.uri("http://webscraper/logo/backgroundimage");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/logo/logofromnested");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canFindRelevantImageAfterH1Tag() {
        final Uri uri = internet.uri("http://webscraper/image/withH1tag");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.google.fr/images/h1.jpg"));
    }

    @Test
    public void slateFrBug() {
        final Uri uri = internet.uri("http://webscraper/bug/slatefr");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.slate.fr/sites/default/files/sarkozy-tv_0.jpg?1328353776"));
    }

    @Test
    public void canFindTextFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/bug/lemondefrnested");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getDescription(), is("h1 text"));
    }

    @Test
    public void canFindRelevantImageBeforeH2Heading() {
        final Uri uri = internet.uri("http://webscraper/bug/10sportbug");

        uriScraper.scrap(uri);

        assertThat(uriScraper.getIllustration(), is("http://www.10sport.com/image.jpg"));
    }

    private UriScraper uriScraper;
}
