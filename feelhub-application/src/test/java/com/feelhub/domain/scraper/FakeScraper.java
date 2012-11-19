package com.feelhub.domain.scraper;

public class FakeScraper extends Scraper {

    @Override
    public ScrapedInformations scrap(final String uri) {
        return new FakeScrapedInformations();
    }
}
