package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.test.FakeInternet;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsScraper {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        scraper = injector.getInstance(Scraper.class);
    }

    @Test
    public void returnEmptyScrapedInformationIfError() {
        final String uri = internet.uri("unknown");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation).isNotNull();
        assertThat(scrapedInformation.getDescription()).isEmpty();
        assertThat(scrapedInformation.getName()).isEmpty();
        assertThat(scrapedInformation.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(scrapedInformation.getPersons()).isEmpty();
    }

    @Test
    public void canScrapLanguage() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getLanguage()).isEqualTo(FeelhubLanguage.fromCode("fr"));
        assertThat(scrapedInformation.languages.size()).isEqualTo(2);
    }

    @Test
    public void canScrapDescription() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getDescription()).isEqualTo("description meta");
        assertThat(scrapedInformation.descriptions.size()).isEqualTo(4);
    }

    @Test
    public void canScrapPersons() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getPersons()).contains("author");
        assertThat(scrapedInformation.getPersons()).contains("designer");
        assertThat(scrapedInformation.getPersons()).contains("owner");
    }

    @Test
    public void canScrapName() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getName()).isEqualTo("name og");
        assertThat(scrapedInformation.names.size()).isEqualTo(5);
    }

    private Scraper scraper;
}
