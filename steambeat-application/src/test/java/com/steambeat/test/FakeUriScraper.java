package com.steambeat.test;

import com.steambeat.domain.scrapers.UriScraper;
import com.steambeat.domain.subject.webpage.Uri;

import java.util.HashMap;

public class FakeUriScraper extends UriScraper {

    @Override
    public void scrap(final Uri uri) {

    }

    public void setScrappedTags(final HashMap<String, String> tags) {
        this.scrapedTags = tags;
    }
}
