package com.steambeat.test;

import com.steambeat.domain.scraper.Scraper;

public class FakeScraper extends Scraper {

    public FakeScraper() {
        super();
    }

    @Override
    public void scrap(final String uri) {

    }

    @Override
    public String getIllustration() {
        return "fakeillustration";
    }
}
