package com.feelhub.domain.scraper.pageanalyzer;

import com.feelhub.test.FakeInternet;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsPageAnalyzer {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        pageAnalyzer = new PageAnalyzer();
    }

    @Test
    public void hasDefaultValuesIfBadUri() {
        final String unknown = internet.uri("unknown");

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(unknown);

        assertThat(scrapedInformations.getIllustration()).isEmpty();
    }

    @Test
    public void canCheckForFirstLevelUri() {
        assertThat(PageAnalyzer.isFirstLevelUri("http://www.google.fr")).isTrue();
        assertThat(PageAnalyzer.isFirstLevelUri("http://www.google.fr/?yeah")).isFalse();
        assertThat(PageAnalyzer.isFirstLevelUri("http://google.fr/#ye")).isFalse();
        assertThat(PageAnalyzer.isFirstLevelUri("http://google.fr")).isTrue();
        assertThat(PageAnalyzer.isFirstLevelUri("google.fr")).isTrue();
        assertThat(PageAnalyzer.isFirstLevelUri("google.fr/lol")).isFalse();
        assertThat(PageAnalyzer.isFirstLevelUri("www.google.fr")).isTrue();
        assertThat(PageAnalyzer.isFirstLevelUri("www.google.fr/bin")).isFalse();
        assertThat(PageAnalyzer.isFirstLevelUri("http://localhost:6162/")).isTrue();
        assertThat(PageAnalyzer.isFirstLevelUri("http://localhost:6162/scraper/logopriority")).isFalse();
    }

    @Test
    public void firstLevelDomainIllustrationPriorityIsLogoThenImage() {
        final String uri = FakeInternet.SERVER_ROOT;

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(uri);

        assertThat(PageAnalyzer.isFirstLevelUri(uri)).isTrue();
        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://localhost:6162/logo.jpg");
    }

    @Test
    public void nonFirstLevelDomainIllustrationPriorityIsImageThenLogo() {
        final String uri = internet.uri("scraper/logopriority");

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(uri);

        assertThat(PageAnalyzer.isFirstLevelUri(uri)).isFalse();
        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://localhost:6162/scraper/image.jpg");
    }

    @Test
    public void domainIllustrationPriorityIsAlwaysOpenGraph() {
        final String uri = internet.uri("scraper/logoprioritywithopengraph");

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(uri);

        assertThat(scrapedInformations.getIllustration()).isEqualTo("http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg");
    }

    @Test
    public void canReturnScrapedType() {
        final String uri = internet.uri("opengraphextractor/illustration");

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(uri);

        assertThat(scrapedInformations.getType()).isEqualTo("video");
    }

    @Test
    public void canGetTitle() {
        final String uri = internet.uri("titleextractor/titletag");

        final ScrapedInformations scrapedInformations = pageAnalyzer.scrap(uri);

        assertThat(scrapedInformations.getTitle()).isEqualTo("Webpage title");
    }

    private PageAnalyzer pageAnalyzer;
}
