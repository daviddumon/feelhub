package com.feelhub.domain.scraper.pageanalyzer.miner;

import com.feelhub.test.FakeInternet;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsCSSMiner {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Test
    public void canOpenADistantCSSFile() {
        final String uri = internet.uri("miner/cssminer/simple");
        final CSSMiner CSSMiner = new CSSMiner(uri);

        final String logoUrl = CSSMiner.scrap("logo");

        assertThat(logoUrl).isEqualTo("logoUrl");
    }
}
