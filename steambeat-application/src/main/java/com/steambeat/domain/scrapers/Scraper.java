package com.steambeat.domain.scrapers;

import com.steambeat.domain.association.Identifier;

public interface Scraper {

    public String getShortDescription();

    public String getDescription();

    public String getIllustration();

    void scrap(Identifier identifier);
}
