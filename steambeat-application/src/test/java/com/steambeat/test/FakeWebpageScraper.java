package com.steambeat.test;

import com.steambeat.domain.scrapers.WebPageScraper;
import com.steambeat.domain.subject.webpage.Uri;

import java.util.HashMap;

public class FakeWebPageScraper extends WebPageScraper {

    @Override
    public void scrap(final Uri uri) {

    }

    public void setScrappedTags(final HashMap<String, String> tags) {
        this.scrapedTags = tags;
    }
}
