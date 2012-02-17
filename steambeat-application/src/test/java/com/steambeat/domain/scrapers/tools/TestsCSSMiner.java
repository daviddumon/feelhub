package com.steambeat.domain.scrapers.tools;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TestsCSSMiner {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canOpenADistantCSSFile() {
        final Uri uri = internet.uri("tools/cssminer/simple");
        final CSSMiner CSSMiner = new CSSMiner(uri);

        final String logoUrl = CSSMiner.scrap("logo");

        assertThat(logoUrl, is("logoUrl"));
    }
}
