package com.feelhub.domain.scraper;

import com.google.inject.Inject;

public class FakeScraper extends Scraper {

    @Inject
    public FakeScraper(final JsoupTitleExtractor jsoupTitleExtractor, final JsoupTagExtractor jsoupTagExtractor, final JsoupAttributExtractor jsoupAttributExtractor) {
        super(jsoupTitleExtractor, jsoupTagExtractor, jsoupAttributExtractor);
    }

    @Override
    public ScrapedInformation scrap(final String uri) {
        return new ScrapedInformation();
    }
}
