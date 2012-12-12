package com.feelhub.domain.scraper.pageanalyzer;

public class FakePageAnalyzer extends PageAnalyzer {

    @Override
    public ScrapedInformations scrap(final String uri) {
        return new FakeScrapedInformations();
    }
}
