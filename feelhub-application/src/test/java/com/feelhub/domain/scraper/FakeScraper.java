package com.feelhub.domain.scraper;

import org.jsoup.nodes.Document;

public class FakeScraper extends Scraper {

    @Override
    public Document scrap(final String uri) {
        return new Document("");
    }
}
