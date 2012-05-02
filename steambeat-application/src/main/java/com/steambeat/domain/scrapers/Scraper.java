package com.steambeat.domain.scrapers;

import com.steambeat.domain.association.uri.Uri;

public interface Scraper {

    public String getShortDescription();

    public String getDescription();

    public String getIllustration();

    void scrap(Uri uri);
}
