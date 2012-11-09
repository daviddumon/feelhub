package com.feelhub.domain.scraper.miner;

import com.feelhub.test.FakeInternet;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsCSSMiner {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Test
    public void canOpenADistantCSSFile() {
        final String uri = internet.uri("miner/cssminer/simple");
        final CSSMiner CSSMiner = new CSSMiner(uri);

        final String logoUrl = CSSMiner.scrap("logo");

        assertThat(logoUrl, is("logoUrl"));
    }
}