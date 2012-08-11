package com.steambeat.domain.scrapers;

import com.steambeat.domain.keyword.Keyword;

public interface Scraper {

    public String getShortDescription();

    public String getDescription();

    public String getIllustration();

    void scrap(Keyword identifier);
}
