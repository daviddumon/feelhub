package com.feelhub.domain.scraper;

import com.feelhub.test.FakeInternet;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsScraper {

    private Scraper scraper;
    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        scraper = new Scraper();
    }

    @Test
    public void hasDefaultValuesIfBadUri() {
        final String unknown = internet.uri("unknown");

        final ScrapedInformations scrapedInformations = scraper.scrap(unknown);

        assertThat(scrapedInformations.getIllustration()).isEmpty();
    }

    @Test
    public void canCheckForFirstLevelUri() {
        assertThat(Scraper.isFirstLevelUri("http://www.google.fr")).isTrue();
        assertThat(Scraper.isFirstLevelUri("http://www.google.fr/?yeah")).isFalse();
        assertThat(Scraper.isFirstLevelUri("http://google.fr")).isTrue();
        assertThat(Scraper.isFirstLevelUri("http://google.fr/#ye")).isFalse();
        assertThat(Scraper.isFirstLevelUri("google.fr")).isTrue();
        assertThat(Scraper.isFirstLevelUri("google.fr/lol")).isFalse();
        assertThat(Scraper.isFirstLevelUri("www.google.fr")).isTrue();
        assertThat(Scraper.isFirstLevelUri("www.google.fr/bin")).isFalse();
        assertThat(Scraper.isFirstLevelUri("http://localhost:6162/")).isTrue();
        assertThat(Scraper.isFirstLevelUri("http://localhost:6162/scraper/logopriority")).isFalse();
    }

    @Test
    public void firstLevelDomainIllustrationPriorityIsLogoThenImage() {
        final String uri = FakeInternet.SERVER_ROOT;
        final Scraper scraper = new Scraper();

        final ScrapedInformations scrapedInformations = scraper.scrap(uri);

        assertThat(Scraper.isFirstLevelUri(uri)).isTrue();
        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://localhost:6162/logo.jpg");
    }

    @Test
    public void nonFirstLevelDomainIllustrationPriorityIsImageThenLogo() {
        final String uri = internet.uri("scraper/logopriority");
        final Scraper scraper = new Scraper();

        final ScrapedInformations scrapedInformations = scraper.scrap(uri);

        assertThat(Scraper.isFirstLevelUri(uri)).isFalse();
        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://localhost:6162/scraper/image.jpg");
    }

    @Test
    public void domainIllustrationPriorityIsAlwaysOpenGraph() {
        final String uri = internet.uri("scraper/logoprioritywithopengraph");
        final Scraper scraper = new Scraper();

        final ScrapedInformations scrapedInformations = scraper.scrap(uri);

        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg");
    }

    @Test
    public void canReturnScrapedType() {
        final String uri = internet.uri("opengraphextractor/illustration");
        final Scraper scraper = new Scraper();

        final ScrapedInformations scrapedInformations = scraper.scrap(uri);

        assertThat(scrapedInformations.getType()).isEqualTo("video");
    }

    @Test
    public void canGetTitle() {
        final String uri = internet.uri("titleextractor/titletag");
        final Scraper scraper = new Scraper();

        final ScrapedInformations scrapedInformations = scraper.scrap(uri);

        assertThat(scrapedInformations.getTitle()).isEqualTo("Webpage title");
    }
}
