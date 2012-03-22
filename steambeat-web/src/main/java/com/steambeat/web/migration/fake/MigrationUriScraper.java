package com.steambeat.web.migration.fake;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.UriScraper;

public class MigrationUriScraper extends UriScraper {

    public MigrationUriScraper() {
        super();
    }

    @Override
    public void scrap(final Uri uri) {

    }

    @Override
    public String getShortDescription() {
        return "link";
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
