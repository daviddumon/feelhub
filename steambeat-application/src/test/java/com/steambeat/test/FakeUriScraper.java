package com.steambeat.test;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.UriScraper;

public class FakeUriScraper extends UriScraper {

    public FakeUriScraper() {
        super();
    }

    @Override
    public void scrap(final Uri uri) {

    }

    @Override
    public String getShortDescription() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getIllustration() {
        return "";
    }
}
