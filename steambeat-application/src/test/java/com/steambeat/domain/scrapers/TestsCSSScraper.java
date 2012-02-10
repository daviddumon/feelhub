package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TestsCSSScraper {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canOpenADistantCSSFile() {
        final Uri uri = internet.uri("http://cssscraper/css/simple");
        final CSSScraper cssScraper = new CSSScraper(uri);

        final String logoUrl = cssScraper.scrap("logo");

        assertThat(logoUrl, is("logoUrl"));
    }
}
