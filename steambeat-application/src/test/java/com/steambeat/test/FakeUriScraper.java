package com.steambeat.test;

import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.webpage.Uri;

public class FakeUriScraper extends UriScraper {

    public FakeUriScraper(final Uri uri) {
        super(uri);
    }

    @Override
    public void scrap() {

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
