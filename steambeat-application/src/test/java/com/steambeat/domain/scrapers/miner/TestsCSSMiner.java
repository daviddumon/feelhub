package com.steambeat.domain.scrapers.miner;

import com.steambeat.domain.uri.Uri;
import com.steambeat.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsCSSMiner {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Test
    public void canOpenADistantCSSFile() {
        final Uri uri = new Uri(internet.uri("miner/cssminer/simple"));
        final CSSMiner CSSMiner = new CSSMiner(uri);

        final String logoUrl = CSSMiner.scrap("logo");

        assertThat(logoUrl, is("logoUrl"));
    }
}
