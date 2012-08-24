package com.steambeat.domain.scraper;

import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsScraper {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Test
    public void hasDefaultValuesIfBadUri() {
        final String unknown = internet.uri("unknown");
        final Scraper scraper = new Scraper();

        scraper.scrap(unknown);

        assertThat(scraper.getIllustration(), is(""));
    }

    @Test
    public void canCheckForFirstLevelUri() {
        assertThat(Scraper.isFirstLevelUri("http://www.google.fr"), is(true));
        assertThat(Scraper.isFirstLevelUri("http://www.google.fr/?yeah"), is(false));
        assertThat(Scraper.isFirstLevelUri("http://google.fr"), is(true));
        assertThat(Scraper.isFirstLevelUri("http://google.fr/#ye"), is(false));
        assertThat(Scraper.isFirstLevelUri("google.fr"), is(true));
        assertThat(Scraper.isFirstLevelUri("google.fr/lol"), is(false));
        assertThat(Scraper.isFirstLevelUri("www.google.fr"), is(true));
        assertThat(Scraper.isFirstLevelUri("www.google.fr/bin"), is(false));
        assertThat(Scraper.isFirstLevelUri("http://localhost:6162/"), is(true));
        assertThat(Scraper.isFirstLevelUri("http://localhost:6162/scraper/logopriority"), is(false));
    }

    @Test
    public void firstLevelDomainIllustrationPriorityIsLogoThenImage() {
        final String uri = FakeInternet.SERVER_ROOT;
        final Scraper scraper = new Scraper();

        scraper.scrap(uri);

        assertThat(Scraper.isFirstLevelUri(uri), is(true));
        assertThat(scraper.getIllustration(), is("http://localhost:6162/logo.jpg"));
    }

    @Test
    public void nonFirstLevelDomainIllustrationPriorityIsImageThenLogo() {
        final String uri = internet.uri("scraper/logopriority");
        final Scraper scraper = new Scraper();

        scraper.scrap(uri);

        assertThat(Scraper.isFirstLevelUri(uri), is(false));
        assertThat(scraper.getIllustration(), is("http://localhost:6162/scraper/image.jpg"));
    }
}
