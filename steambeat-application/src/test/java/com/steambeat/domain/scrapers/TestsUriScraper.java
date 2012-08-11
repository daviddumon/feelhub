package com.steambeat.domain.scrapers;

import com.steambeat.domain.uri.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsUriScraper {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();
    //
    //@Test
    //public void canFindSimpleDescriptionFromTitle() {
    //    final Uri uri = new Uri(internet.uri("titleextractor/titletag"));
    //    final UriScraper uriScraper = new UriScraper();
    //
    //    uriScraper.scrap(uri);
    //
    //    assertThat(uriScraper.getDescription(), is("Webpage title"));
    //}
    //
    //@Test
    //public void hasDefaultValuesIfBadUri() {
    //    final Uri uri = new Uri(internet.uri("unknown"));
    //    final UriScraper uriScraper = new UriScraper();
    //
    //    uriScraper.scrap(uri);
    //
    //    assertThat(uriScraper.getDescription(), is("http://localhost:6162/unknown"));
    //    assertThat(uriScraper.getShortDescription(), is("http://localhost:6162/unknown"));
    //    assertThat(uriScraper.getIllustration(), is(""));
    //}
    //
    //@Test
    //public void canFindShortDescription() {
    //    final UriScraper uriScraper1 = new UriScraper();
    //    final UriScraper uriScraper2 = new UriScraper();
    //    final UriScraper uriScraper3 = new UriScraper();
    //
    //    uriScraper1.scrap(new Uri(internet.uri("titleextractor/titletag")));
    //    assertThat(uriScraper1.getShortDescription(), is("Webpage title"));
    //
    //    uriScraper2.scrap(new Uri(internet.uri("lastelementextractor/h1tag")));
    //    assertThat(uriScraper2.getShortDescription(), is("Second section"));
    //
    //    uriScraper3.scrap(new Uri(internet.uri("logoextractor/withclasslogo")));
    //    assertThat(uriScraper3.getShortDescription(), is("localhost:6162[...]"));
    //}
    //
    //@Test
    //public void firstLevelDomainIllustrationPriorityIsLogoThenImage() {
    //    final Uri uri = new Uri(internet.uri("/"));
    //    final UriScraper uriScraper = new UriScraper();
    //
    //    uriScraper.scrap(uri);
    //
    //    assertThat(uri.isFirstLevelUri(), is(true));
    //    assertThat(uriScraper.getIllustration(), is("http://localhost:6162/logo.jpg"));
    //}
    //
    //@Test
    //public void nonFirstLevelDomainIllustrationPriorityIsImageThenLogo() {
    //    final Uri uri = new Uri(internet.uri("uriscraper/logopriority"));
    //    final UriScraper uriScraper = new UriScraper();
    //
    //    uriScraper.scrap(uri);
    //
    //    assertThat(uri.isFirstLevelUri(), is(false));
    //    assertThat(uriScraper.getIllustration(), is("http://localhost:6162/uriscraper/image.jpg"));
    //}
}
