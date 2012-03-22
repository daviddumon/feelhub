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
        return "fakeshortdescription";
    }

    @Override
    public String getDescription() {
        return "token1 token2 token3";
    }

    @Override
    public String getIllustration() {
        return "fakeillustration";
    }
}
