package com.steambeat.test;

import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.webpage.Uri;

public class FakeUriScraper extends UriScraper {

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
