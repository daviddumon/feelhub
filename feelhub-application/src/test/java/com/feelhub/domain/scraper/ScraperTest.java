package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.test.FakeInternet;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class ScraperTest {

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

        assertThat(scrapedInformation.getDescription()).isEqualTo("description og");
        assertThat(scrapedInformation.descriptions.size()).isEqualTo(5);
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

    @Test
    public void canScrapImages() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getImages().size()).isEqualTo(5);
    }

    @Test
    public void canBuildImagesFromRelativeUris() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getImages().get(4)).isEqualTo(new Uri(uri).getCorrectProtocol() + new Uri(uri).getDomain() + "/image/2013/01/25/540x270/1822689_3_8a6a_un-jeune-manifestant-vendredi-place-tahrir_463393f7fa4747ea1908e251224820e7.jpg");
    }

    @Test
    public void forceProtocol() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(new Uri(scrapedInformation.getImages().get(3)).hasProtocol()).isTrue();
    }

    @Test
    public void canScrapVideos() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getVideos().size()).isEqualTo(1);
    }

    @Test
    public void canScrapAudio() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getAudios().size()).isEqualTo(1);
    }

    @Test
    public void canScrapType() {
        final String uri = internet.uri("scraper");

        final ScrapedInformation scrapedInformation = scraper.scrap(uri);

        assertThat(scrapedInformation.getType()).isEqualTo(HttpTopicType.Article);
    }

    private Scraper scraper;
}
