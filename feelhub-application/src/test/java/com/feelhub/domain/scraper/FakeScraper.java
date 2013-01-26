package com.feelhub.domain.scraper;

public class FakeScraper extends Scraper {

    @Override
    public ScrapedInformation scrap(final String uri) {
        final ScrapedInformation scrapedInformation = new ScrapedInformation();
        scrapedInformation.addName(100, "name");
        return scrapedInformation;
    }
}
