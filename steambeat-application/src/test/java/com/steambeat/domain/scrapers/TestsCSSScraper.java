package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestsCSSScraper {

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Test
    public void canOpenADistantCSSFile() {
        final Uri uri = internet.uri("http://cssscraper/css/simple");
        final CSSScraper cssScraper = new CSSScraper(uri);

        final String logoUrl = cssScraper.scrap("logo");

        assertThat(logoUrl, is("logoUrl"));
    }
}
