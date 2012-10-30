package com.feelhub.domain.scraper;

public class FakeScraper extends Scraper {

    @Override
    public void scrap(final String uri) {

    }

    @Override
    public String getIllustration() {
        return "fakeillustration";
    }
}
