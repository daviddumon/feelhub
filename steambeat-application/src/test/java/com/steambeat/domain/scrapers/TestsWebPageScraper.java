package com.steambeat.domain.scrapers;

import com.steambeat.domain.scrapers.extractor.*;
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
    public void canFindTitleTag() {
        final Uri uri = internet.uri("http://webscraper/titletag");
        webPageScraper.addExtractor(new TitleExtractor());

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is("Webpage title"));
    }

    @Test
    public void continueIfBadUri() {
        final Uri uri = internet.uri("unknown");
        webPageScraper.addExtractor(new TitleExtractor());
        webPageScraper.addExtractor(new LastElementExtractor("h1", "h1"));
        webPageScraper.addExtractor(new FirstElementExtractor("h2", "h2"));
        webPageScraper.addExtractor(new LogoExtractor("logo"));
        webPageScraper.addExtractor(new ImageExtractor("image"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is(""));
        assertThat(webPageScraper.getScrapedTags().get("h1"), is(""));
        assertThat(webPageScraper.getScrapedTags().get("h2"), is(""));
        assertThat(webPageScraper.getScrapedTags().get("logo"), is(""));
        assertThat(webPageScraper.getScrapedTags().get("image"), is(""));
    }

    @Test
    public void canFindTitleTagWithBadHtml() {
        final Uri uri = internet.uri("http://webscraper/titletagbadhtml");
        webPageScraper.addExtractor(new TitleExtractor());

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("title"), is("Webpage title"));
    }

    @Test
    public void canFindH1Tag() {
        final Uri uri = internet.uri("http://webscraper/h1tag");
        webPageScraper.addExtractor(new LastElementExtractor("h1", "h1"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("h1"), is("Second section"));
    }

    @Test
    public void canFindH2Tag() {
        final Uri uri = internet.uri("http://webscraper/h2tag");
        webPageScraper.addExtractor(new FirstElementExtractor("h2", "h2"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("h2"), is("First h2 section"));
    }

    @Test
    public void canFindLogoWithClass() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogo");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithId() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogo");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogo");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogopattern");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogopattern");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogopattern");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final Uri uri = internet.uri("http://webscraper/logo/backgroundimage");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/logo/logofromnested");
        webPageScraper.addExtractor(new LogoExtractor("logo"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("logo"), is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canFindRelevantImageAfterH1Tag() {
        final Uri uri = internet.uri("http://webscraper/image/withH1tag");
        webPageScraper.addExtractor(new ImageExtractor("image"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("image"), is("http://www.google.fr/images/h1.jpg"));
    }

    @Test
    public void slateFrBug() {
        final Uri uri = internet.uri("http://webscraper/bug/slatefr");
        webPageScraper.addExtractor(new ImageExtractor("image"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("image"), is("http://www.slate.fr/sites/default/files/sarkozy-tv_0.jpg?1328353776"));
    }

    @Test
    public void canFindTextFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/bug/lemondefrnested");
        webPageScraper.addExtractor(new FirstElementExtractor("h1", "h1"));
        webPageScraper.addExtractor(new LastElementExtractor("h2", "h2"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("h1"), is("h1 text"));
        assertThat(webPageScraper.getScrapedTags().get("h2"), is("h2 text"));
    }

    @Test
    public void canFindRelevantImageBeforeH2Heading() {
        final Uri uri = internet.uri("http://webscraper/bug/10sportbug");
        webPageScraper.addExtractor(new ImageExtractor("image"));

        webPageScraper.scrap(uri);

        assertThat(webPageScraper.getScrapedTags().get("image"), is("http://www.10sport.com/image.jpg"));
    }

    private WebPageScraper webPageScraper;
}
